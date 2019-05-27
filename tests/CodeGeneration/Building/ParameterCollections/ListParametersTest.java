package CodeGeneration.Building.ParameterCollections;

import CodeGeneration.Building.Parameter;
import CodeGeneration.Building.Statements.VariableDeclarations.BlockDeclaration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ListParametersTest {

    // Fields:
    private ListParameters listParameters;
    private Parameter parameter;

    // Constant:
    private static final String DEFAULT_ID = "id";

    @BeforeEach
    void beforeEach() {
        this.listParameters = new ListParameters();
        this.parameter = new BlockDeclaration(DEFAULT_ID);
    }

    @Test
    void addParameter() {
        this.listParameters.addParameter(this.parameter);
        assertTrue(this.listParameters.getParameterList().contains(this.parameter));
    }

    @Test
        // Assert that the list does not contain a parameter, which wasn't addded
    void getParameterList() {
        assertFalse(this.listParameters.getParameterList().contains(this.parameter));
    }

    @Test
    void toCallParameters01() {
        assertNotNull(this.listParameters.toCallParameters());
    }

    @Test
        // With parameter
    void toCallParameters02() {
        this.listParameters.addParameter(this.parameter);
        assertNotNull(this.listParameters.toCallParameters());
    }

    @Test
    void toString01() {
        assertNotNull(this.listParameters.toString());
    }

    @Test
        // With parameter
    void toString02() {
        this.listParameters.addParameter(this.parameter);
        assertNotNull(this.listParameters.toString());
    }
}