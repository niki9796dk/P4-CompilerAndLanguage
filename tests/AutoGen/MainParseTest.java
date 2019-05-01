package AutoGen;

import SemanticAnalysis.SemanticProblemException;
import TypeChecker.Exceptions.TypeInconsistencyException;
import org.junit.jupiter.api.*;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class MainParseTest {

    // Test all positive files
    @TestFactory
    Stream<DynamicTest> positiveFiles() {

        File trueFolder = new File("tests/ExpectTrue/");

        List<File> trueFiles = Arrays.asList(Objects.requireNonNull(trueFolder.listFiles()));

        return trueFiles.stream()
            .map(file -> DynamicTest.dynamicTest("Testing: '" + file.getName() + "'",
                () -> assertTrue(MainParse.parseFile(file.getPath()))));
    }

    // Test all negative files
    @TestFactory
    Stream<DynamicTest> negativeFiles_ProductionRules() {
        File falseFolder = new File("tests/ProductionRules/ExpectFalse/");

        return expectedFalse(falseFolder, AutoGen.ScannerException.class);
    }

    // Test all negative files
    @TestFactory
    Stream<DynamicTest> negativeFiles_TypeChecking() {
        File falseFolder = new File("tests/TypeRules/ExpectFalse/");

        return expectedFalse(falseFolder, TypeInconsistencyException.class);
    }

    // Test all negative files regarding scope checking
    @TestFactory
    Stream<DynamicTest> scopeNegativeFiles() {
        File falseFolder = new File("tests/ScopeChecking/ExpectFalse/");

        return expectedFalse(falseFolder, AST.TreeWalks.Exceptions.ScopeBoundsViolationException.class);
    }

    // Test all negative files regarding scope checking
    @TestFactory
    Stream<DynamicTest> semanticNegativeFiles() {
        File falseFolder = new File("tests/SemanticAnalysis/ExpectFalse/");

        return expectedFalse(falseFolder, SemanticProblemException.class);
    }

    // General test factory for false tests
    private Stream<DynamicTest> expectedFalse(File filePath, Class expectedExceptionClass) {

        List<File> falseFiles = Arrays.asList(Objects.requireNonNull(filePath.listFiles()));

        return falseFiles.stream()
                .map(file -> DynamicTest.dynamicTest("Testing: '" + file.getName() + "'",
                        () -> assertThrows(expectedExceptionClass, () -> MainParse.parseFile(file.getPath()))));
    }
}

