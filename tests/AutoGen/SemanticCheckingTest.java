package AutoGen;

import CompilerExceptions.SemanticExceptions.*;
import DataStructures.Pair;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;

import java.util.stream.Stream;

public class SemanticCheckingTest {
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

        return MainParseTest.multipleExpectFalse(mainPath, pathExceptionPair);
    }
}
