package DynamicProgramTests;

import AutoGen.MainParse;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;

import java.io.File;
import java.nio.file.NoSuchFileException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

public class PositiveFilesTest {
    private static final String CODE_GEN_FOLDER = "src" + File.separator + "AutoGen" + File.separator + "CodeGen";

    // Test all positive files
    @TestFactory
    Stream<DynamicTest> positiveFiles_TRUE() {

        File trueFolder = new File("tests/ExpectTrue/");

        List<File> trueFiles = Arrays.asList(Objects.requireNonNull(trueFolder.listFiles()));

        return trueFiles.stream()
                .map(file -> DynamicTest.dynamicTest("Testing: '" + file.getName() + "'",
                        () -> Assertions.assertTrue(MainParse.compileProgram(file.getPath()))));
    }

    @AfterAll
    static void cleanCodeGenSubDirectories() throws NoSuchFileException {
        File codeGenDir = new File(CODE_GEN_FOLDER);

        File[] subDirectories = new File(codeGenDir.getPath()).listFiles(File::isDirectory);

        List<File> programFiles = new ArrayList<>();

        for (File subdir : subDirectories) {
            programFiles.addAll(Arrays.asList(Objects.requireNonNull(subdir.listFiles())));
        }

        // Delete all files
        for (File file : programFiles) {
            if (!file.delete()) {
                throw new NoSuchFileException("Unable to delete file: " + file.getPath());
            }
        }

        // Delete all directories
        for (File subDir : subDirectories) {
            if (!subDir.delete()) {
                throw new NoSuchFileException("Unable to delete sub-directory: " + subDir.getPath());
            }
        }
    }
}
