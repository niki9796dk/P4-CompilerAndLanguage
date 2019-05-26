package DynamicProgramTests;

import AutoGen.MainParse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class PositiveFilesTest {
    // Test all positive files
    @TestFactory
    Stream<DynamicTest> positiveFiles_TRUE() {

        File trueFolder = new File("tests/ExpectTrue/");

        List<File> trueFiles = Arrays.asList(Objects.requireNonNull(trueFolder.listFiles()));

        return trueFiles.stream()
                .map(file -> DynamicTest.dynamicTest("Testing: '" + file.getName() + "'",
                        () -> Assertions.assertTrue(MainParse.compileProgram(file.getPath()))));
    }
}
