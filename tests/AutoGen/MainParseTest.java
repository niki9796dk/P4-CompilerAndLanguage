package AutoGen;

import CodeGeneration.DataFlow.Network.Nodes.Block;
import CompilerExceptions.ScopeExceptions.*;
import CodeGeneration.DataFlow.Network.Nodes.Block;
import CompilerExceptions.ScopeExceptions.IllegalProcedureCallScopeException;
import CompilerExceptions.ScopeExceptions.NoSuchBlockDeclaredException;
import CompilerExceptions.ScopeExceptions.NoSuchVariableDeclaredException;
import CompilerExceptions.ScopeExceptions.VariableAlreadyDeclaredException;
import CompilerExceptions.SemanticExceptions.*;
import CompilerExceptions.TypeExceptions.*;
import DataStructures.Pair;
import LinearAlgebra.Statics.Matrices;
import org.junit.jupiter.api.*;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.lang.reflect.Array;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class MainParseTest {
    static Stream<DynamicTest> multipleExpectFalse(String mainPath, Pair<String, Class>[] pathExceptionPair) {
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
    static Stream<DynamicTest> expectedFalse(File filePath, Class expectedExceptionClass) {

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
































