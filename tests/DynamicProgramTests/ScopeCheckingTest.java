package DynamicProgramTests;

import CompilerExceptions.ScopeExceptions.*;
import DataStructures.Pair;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;

import java.util.stream.Stream;

public class ScopeCheckingTest {
    // Test all negative files regarding scope checking
    @TestFactory
    Stream<DynamicTest> negativeFiles_ScopeChecking() {
        String mainPath = "tests/ScopeChecker/ExpectFalse/";

        Pair[] pathExceptionPair = {
                new Pair<>("IllegalProcedureCallScopeException/", IllegalProcedureCallScopeException.class),
                new Pair<>("VariableAlreadyDeclaredException/", VariableAlreadyDeclaredException.class),
                new Pair<>("NoSuchVariableDeclaredException/", NoSuchVariableDeclaredException.class),
                new Pair<>("NoSuchBlockDeclaredException/", NoSuchBlockDeclaredException.class),
                new Pair<>("IllegalBlockNameException/", IllegalBlockNameException.class),
        };

        return ExpectFalseHelper.multipleExpectFalse(mainPath, pathExceptionPair);
    }
}
