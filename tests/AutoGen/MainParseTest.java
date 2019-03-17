package AutoGen;

import jflex.ScannerException;
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
    Stream<DynamicTest> negativeFiles() {

        File trueFolder = new File("tests/ProductionRules/ExpectFalse/");

        List<File> trueFiles = Arrays.asList(Objects.requireNonNull(trueFolder.listFiles()));

        return trueFiles.stream()
                .map(file -> DynamicTest.dynamicTest("Testing: '" + file.getName() + "'",
                        () -> assertThrows(AutoGen.ScannerException.class, () -> assertTrue(MainParse.parseFile(file.getPath())))));
    }
}

