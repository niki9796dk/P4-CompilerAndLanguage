package DynamicProgramTests;

import AutoGen.MainParse;
import CodeGeneration.DataFlow.Network.Nodes.Block;
import LinearAlgebra.Statics.Matrices;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class CodeGenRunTest {
    private static final String TRUE_FOLDER = "tests" + File.separator + "ExpectTrue" + File.separator;
    private static final String CODE_GEN_FOLDER = "src" + File.separator + "AutoGen" + File.separator + "CodeGen";

    // Test all positive files
    @TestFactory
    Stream<DynamicTest> codeGenRun_TRUE()  {
        // Compile all trueFiles to test that they can be run
        this.compileTrueFiles();

        // Get the java compiler and compile all the code-generated java files into .class files
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        compiler.run(null, null, null, this.getAllBlockPaths());

        ClassLoader classLoader;
        try {
            classLoader = new URLClassLoader(new URL[]{new File("src"+File.separator).toURI().toURL()});
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }

        File codeGenTrueFolder = new File(CODE_GEN_FOLDER);
        File[] subDirectories = new File(codeGenTrueFolder.getPath()).listFiles(File::isDirectory);

        // Make empty stream of dynamic tests to use later
        Stream<DynamicTest> testStream = Stream.empty();

        // Go through all directories with class files in them
        for(File subDir : subDirectories) {
            List<File> filesInDir = Arrays.asList(Objects.requireNonNull(subDir.listFiles()));

            // Make a stream of all class files to run (Valid main blocks)
            Stream<DynamicTest> testInDir = filesInDir.stream()
                    .filter(file -> file.getName().contains(".class"))
                    .filter(file -> this.isValidMainBlock(file, classLoader))
                    .map(file -> DynamicTest.dynamicTest("Testing [" + subDir.getName() + "]: '" + file.getName() + "'",
                            () -> testAutoGenBlock(getBlockClassFromFile(file, classLoader))));

            testStream = Stream.concat(testStream, testInDir);
        }

        return testStream;
    }

    private String[] getAllBlockPaths() {
        File dir = new File(CODE_GEN_FOLDER);

        File[] subDirectories = new File(dir.getPath()).listFiles(File::isDirectory);

        List<File> programFiles = new ArrayList<>();
        for(File subdir : subDirectories) {
            programFiles.addAll(Arrays.asList(Objects.requireNonNull(subdir.listFiles())));
        }

        return programFiles.stream()
                .map(File::getPath)
                .filter(string -> string.endsWith(".java"))
                .toArray(String[]::new);
    }

    private void testAutoGenBlock(Class<Block> blockClass) {
        // I stream af classes (.filter(getConstructor)) (No parameters, 1 input og 1 output
        try {
            Block instance = blockClass.newInstance();

            instance.train(Matrices.randomMatrix(1, 1, 1234), Matrices.randomMatrix(1, 1, 4321), 2);
            instance.evaluateInput(Matrices.randomMatrix(1, 1, 1234));

            // No exception when training.
            assertTrue(true);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private boolean isValidMainBlock(File file, ClassLoader classLoader) {
        Class<Block> blockClass;
        try {
            blockClass = getBlockClassFromFile(file, classLoader);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("No class found within the path " + file.getPath() + "\nShould not happen");
        }

        try {
            // Check that an constructor with no arguments exist - Otherwise an exception is thrown.
            blockClass.getConstructor();

            // Get a new instance of the class, from the default no argument constructor
            Block block = blockClass.newInstance();

            if (block.getInputChannels().size() != 1 || block.getOutputChannels().size() != 1) {
                return false;
            }
        } catch (Exception e) {
            return false;
        }

        return true;
    }

    @SuppressWarnings("unchecked")
    private Class<Block> getBlockClassFromFile(File file, ClassLoader classLoader) throws ClassNotFoundException {
        System.out.println(file.getName() + "  "  + file.getPath() + "  " + file.exists() + "  ");

        return  (Class<Block>) Class.forName(file.getPath()
                .replace(File.separatorChar, '.')
                /*
                .replace('/', '.')
                .replace('\\', '.')
                */
                .replaceAll("src.", "")
                .replaceAll(".class", ""), false, classLoader);
    }

    private void compileTrueFiles() {
        File trueFolder = new File(TRUE_FOLDER);
        List<File> trueFiles = Arrays.asList(Objects.requireNonNull(trueFolder.listFiles()));

        trueFiles.forEach(file -> {
            try {
                MainParse.compileProgram(file.getPath());
            } catch (Exception e) {
                System.out.println("The true test: [" + file.getName() + "] has failed");
            }
        });
    }
}
