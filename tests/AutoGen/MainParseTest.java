package AutoGen;

import CodeGeneration.DataFlow.Network.Nodes.Block;
import CompilerExceptions.ScopeExceptions.*;
import CodeGeneration.DataFlow.Network.Nodes.Block;
import CompilerExceptions.ScopeExceptions.IllegalProcedureCallScopeException;
import CompilerExceptions.ScopeExceptions.NoSuchBlockDeclaredException;
import CompilerExceptions.ScopeExceptions.NoSuchVariableDeclaredException;
import CompilerExceptions.ScopeExceptions.VariableAlreadyDeclaredException;
import CompilerExceptions.SemanticExceptions.*;
import CompilerExceptions.TypeExceptions.*;
import DataStructures.Pair;
import LinearAlgebra.Statics.Matrices;
import org.junit.jupiter.api.*;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.lang.reflect.Array;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class MainParseTest {
    static Stream<DynamicTest> multipleExpectFalse(String mainPath, Pair<String, Class>[] pathExceptionPair) {
        if ("".equals(mainPath)) {
            throw new IllegalArgumentException("No main path given");
        }

        List<Stream<DynamicTest>> testStreams = new ArrayList<>();

        for (Pair<String, Class> stringClassPair : pathExceptionPair) {
            File filePath = new File(mainPath + stringClassPair.getKey());
            Class exception = stringClassPair.getValue();

            testStreams.add(expectedFalse(filePath, exception));
        }

        Stream<DynamicTest> finalTestSteam = Stream.empty();

        for (Stream<DynamicTest> stream : testStreams) {
            finalTestSteam = Stream.concat(finalTestSteam, stream);
        }

        return finalTestSteam;
    }

    // General test factory for false tests
    static Stream<DynamicTest> expectedFalse(File filePath, Class expectedExceptionClass) {

        List<File> falseFiles = Arrays.stream(
                Objects.requireNonNull(
                        filePath.listFiles()
                ))
                .filter((File::isFile))
                .collect(Collectors.toList()
        );

        return falseFiles.stream()
                .map(file -> DynamicTest.dynamicTest("Testing: '" + file.getName() + "'",
                        () -> assertThrows(expectedExceptionClass, () -> MainParse.parseFile(file.getPath()))));
    }

    // Test all positive files
    @TestFactory
    Stream<DynamicTest> codeGen_TRUE() {

        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();

        compiler.run(null, null, null, getAllBlockPaths());

        File codeGenTrueFolder = new File("src/AutoGen/CodeGen/");

        File[] subDirectories = new File(codeGenTrueFolder.getPath()).listFiles(File::isDirectory);

        Stream<DynamicTest> testStream = Stream.empty();

        for(File subDir : subDirectories) {
            List<File> filesIndir = Arrays.asList(Objects.requireNonNull(subDir.listFiles()));

            Stream<DynamicTest> testInDir = filesIndir.stream()
                    .filter(file -> file.getName().contains(".class"))
                    .filter(this::isValidMainBlock)
                    .map(file -> DynamicTest.dynamicTest("Testing [" + subDir.getName() + "]: '" + file.getName() + "'",
                            () -> testAutoGenBlock(getBlockClassFromFile(file))));

            testStream = Stream.concat(testStream, testInDir);
        }

        return testStream;
    }

    private String[] getAllBlockPaths() {
        File dir = new File("src/AutoGen/CodeGen");

        File[] subDirectories = new File(dir.getPath()).listFiles(File::isDirectory);

        List<File> programFiles = new ArrayList<>();
        for(File subdir : subDirectories) {
            programFiles.addAll(Arrays.asList(Objects.requireNonNull(subdir.listFiles())));
        }

        return programFiles.stream().map(File::getPath).filter(string -> string.endsWith(".java")).toArray(String[]::new);
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

    private boolean isValidMainBlock(File file) {
        Class<Block> blockClass = null;
        try {
            blockClass = getBlockClassFromFile(file);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("No class found with in the path " + file.getPath() + "\nShould not happen, implementation is wrong");
        }

        try {
            blockClass.getConstructor();
        } catch (NoSuchMethodException e) {
            return false;
        }

        return true;
    }

    @SuppressWarnings("unchecked")
    private Class<Block> getBlockClassFromFile(File file) throws ClassNotFoundException {
        return  (Class<Block>) Class.forName(file.getPath()
                .replace('/', '.')
                .replaceAll("src.", "")
                .replaceAll(".class", ""));
    }
}
































