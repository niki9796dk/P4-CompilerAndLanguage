package DynamicProgramTests;

import CompilerExceptions.TypeExceptions.*;
import DataStructures.Pair;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;

import java.util.stream.Stream;

public class TypeCheckingTest {
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

        return ExpectFalseHelper.multipleExpectFalse(mainPath, pathExceptionPair);
    }
}
