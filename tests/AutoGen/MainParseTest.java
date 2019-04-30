package AutoGen;

import TypeChecker.Exceptions.TypeInconsistencyException;
import org.junit.jupiter.api.*;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class MainParseTest {

    /*
    @BeforeAll
    static void setTestFlag(){
        MainParse.isTest = true;
    }

    @AfterAll
    static void resetTestFlag(){
        MainParse.isTest = false;
    }
    */

    // Test all positive files
    @TestFactory
    Stream<DynamicTest> positiveFiles() {

        File trueFolder = new File("tests/ProductionRules/ExpectTrue/");

        List<File> trueFiles = Arrays.asList(Objects.requireNonNull(trueFolder.listFiles()));

        return trueFiles.stream()
            .map(file -> DynamicTest.dynamicTest("Testing: '" + file.getName() + "'",
                () -> assertTrue(MainParse.parseFile(file.getPath()))));
    }

    // Test all negative files
    @TestFactory
    Stream<DynamicTest> negativeFiles_ProductionRules() {
        File trueFolder = new File("tests/ProductionRules/ExpectFalse/");

        return expectedFalse(trueFolder, AutoGen.ScannerException.class);
    }

    // Test all negative files
    @TestFactory
    Stream<DynamicTest> negativeFiles_TypeChecking() {
        File trueFolder = new File("tests/TypeRules/ExpectFalse/");

        return expectedFalse(trueFolder, TypeInconsistencyException.class);
    }

    // General test factory for false tests
    private Stream<DynamicTest> expectedFalse(File filePath, Class expectedExceptionClass) {

        List<File> trueFiles = Arrays.asList(Objects.requireNonNull(filePath.listFiles()));

        return trueFiles.stream()
                .map(file -> DynamicTest.dynamicTest("Testing: '" + file.getName() + "'",
                        () -> assertThrows(expectedExceptionClass, () -> assertTrue(MainParse.parseFile(file.getPath())))));
    }
}

