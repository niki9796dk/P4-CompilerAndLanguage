package AutoGen;

import CompilerExceptions.ScopeExceptions.IllegalProcedureCallScopeException;
import CompilerExceptions.ScopeExceptions.NoSuchBlockDeclaredException;
import CompilerExceptions.ScopeExceptions.NoSuchVariableDeclaredException;
import CompilerExceptions.ScopeExceptions.VariableAlreadyDeclaredException;
import CompilerExceptions.SemanticExceptions.*;
import CompilerExceptions.TypeExceptions.*;
import DataStructures.Pair;
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

    // Test all negative files in syntax analysis phase
    @TestFactory
    Stream<DynamicTest> negativeFiles_SyntaxAnalysis() {
        File falseFolder = new File("tests/SyntaxAnalysis/ExpectFalse/");

        return expectedFalse(falseFolder, AutoGen.SyntaxAnalysisException.class);
    }

    // Test all negative files regarding type checking
    @TestFactory
    Stream<DynamicTest> negativeFiles_TypeChecking() {
        String mainPath = "tests/TypeRules/ExpectFalse/";

        Pair[] pathExceptionPair = {
                new Pair<>("", TypeInconsistencyException.class),
                new Pair<>("IncorrectAssignmentTypesException/", IncorrectAssignmentTypesException.class),
                new Pair<>("ParamsSizeInconsistencyException/", ParamsSizeInconsistencyException.class),
                new Pair<>("ParamsTypeInconsistencyException/", ParamsTypeInconsistencyException.class),
                new Pair<>("ChannelPlacementTypeException/", ChannelPlacementTypeException.class),
        };

        return multipleExpectFalse(mainPath, pathExceptionPair);
    }

    // Test all negative files regarding scope checking
    @TestFactory
    Stream<DynamicTest> negativeFiles_ScopeChecking() {
        String mainPath = "tests/ScopeChecker/ExpectFalse/";

        Pair[] pathExceptionPair = {
                new Pair<>("IllegalProcedureCallScopeException/", IllegalProcedureCallScopeException.class),
                new Pair<>("VariableAlreadyDeclaredException/", VariableAlreadyDeclaredException.class),
                new Pair<>("NoSuchVariableDeclaredException/", NoSuchVariableDeclaredException.class),
                new Pair<>("NoSuchBlockDeclaredException/", NoSuchBlockDeclaredException.class),
        };

        return multipleExpectFalse(mainPath, pathExceptionPair);
    }

    // Test all negative files regarding scope checking
    @TestFactory
    Stream<DynamicTest> negativeFiles_SemanticChecking() {
        String mainPath = "tests/SemanticAnalysis/ExpectFalse/";

        Pair[] pathExceptionPair = {
                new Pair<>("", SemanticProblemException.class),
                new Pair<>("BuildRecursionException/", BuildRecursionException.class),
                new Pair<>("NoMainBlockException/", NoMainBlockException.class),
                new Pair<>("GroupConnectionMismatchException/", GroupConnectionMismatchException.class),
                new Pair<>("ChainConnectionMismatchException/", ChainConnectionMismatchException.class),
                new Pair<>("IncorrectChannelUsageException/", IncorrectChannelUsageException.class),
        };

        return multipleExpectFalse(mainPath, pathExceptionPair);
    }

    private Stream<DynamicTest> multipleExpectFalse(String mainPath, Pair<String, Class>[] pathExceptionPair) {
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

