package AutoGen;

import SemanticAnalysis.Exceptions.*;
import TypeChecker.Exceptions.*;
import org.junit.jupiter.api.*;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class MainParseTest {

    // Test all positive files
    @TestFactory
    Stream<DynamicTest> positiveFiles_TRUE() {

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

    // Test all negative files regarding type checking
    @TestFactory
    Stream<DynamicTest> negativeFiles_TypeChecking() {
        String mainPath = "tests/TypeRules/ExpectFalse/";

        String[] paths = {
                "",
                "IncorrectAssignmentTypesException/",
                "ParamsSizeInconsistencyException/",
                "ParamsTypeInconsistencyException/",
                "ChannelPlacementTypeException/",
        };

        Class[] exceptions = {
                TypeInconsistencyException.class,
                IncorrectAssignmentTypesException.class,
                ParamsSizeInconsistencyException.class,
                ParamsTypeInconsistencyException.class,
                ChannelPlacementTypeException.class,
        };

        return multipleExpectFalse(mainPath, paths, exceptions);
    }

    // Test all negative files regarding scope checking
    @TestFactory
    Stream<DynamicTest> negativeFiles_ScopeChecking() {
        File falseFolder = new File("tests/ScopeChecking/ExpectFalse/");

        return expectedFalse(falseFolder, AST.TreeWalks.Exceptions.ScopeBoundsViolationException.class);
    }

    // Test all negative files regarding scope checking
    @TestFactory
    Stream<DynamicTest> negativeFiles_SemanticChecking() {
        String mainPath = "tests/SemanticAnalysis/ExpectFalse/";

        String[] paths = {
                "",
                "BuildRecursionException/",
                "NoMainBlockException/",
                "GroupConnectionMismatchException/",
                "ChainConnectionMismatchException/",
                "IncorrectChannelUsageException/",
        };

        Class[] exceptions = {
                SemanticProblemException.class,
                BuildRecursionException.class,
                NoMainBlockException.class,
                GroupConnectionMismatchException.class,
                ChainConnectionMismatchException.class,
                IncorrectChannelUsageException.class,
        };

        return multipleExpectFalse(mainPath, paths, exceptions);
    }

    private Stream<DynamicTest> multipleExpectFalse(String mainPath, String[] paths, Class[] exceptions) {
        if ("".equals(mainPath)) {
            throw new IllegalArgumentException("No main path given");
        }

        if (paths.length == 0 || paths.length != exceptions.length) {
            throw new IllegalArgumentException("Uneven amount of paths and exceptions or non given at all!");
        }

        List<Stream<DynamicTest>> testStreams = new ArrayList<>();

        for (int i = 0; i < paths.length; i++) {
            File filePath = new File(mainPath + paths[i]);
            Class exception = exceptions[i];

            testStreams.add(expectedFalse(filePath, exception));
        }

        Stream<DynamicTest> finalTestSteam = Stream.empty();

        for (Stream<DynamicTest> stream : testStreams) {
            finalTestSteam = Stream.concat(finalTestSteam, stream);
        }

        return finalTestSteam;
    }

    // General test factory for false tests
    private Stream<DynamicTest> expectedFalse(File filePath, Class expectedExceptionClass) {

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
}

