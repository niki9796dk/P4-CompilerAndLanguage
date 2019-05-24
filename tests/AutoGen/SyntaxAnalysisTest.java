package AutoGen;

import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;

import java.io.File;
import java.util.stream.Stream;

public class SyntaxAnalysisTest {

    // Test all negative files in syntax analysis phase
    @TestFactory
    Stream<DynamicTest> negativeFiles_SyntaxAnalysis() {
        File falseFolder = new File("tests/SyntaxAnalysis/ExpectFalse/");

        return MainParseTest.expectedFalse(falseFolder, AutoGen.SyntaxAnalysisException.class);
    }
}
