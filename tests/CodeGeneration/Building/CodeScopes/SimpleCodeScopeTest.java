package CodeGeneration.Building.CodeScopes;

import CodeGeneration.Building.ParameterCollection;
import CodeGeneration.Building.Statement;
import CodeGeneration.Building.Statements.MyChannelDeclarations.BlockChannelDeclarationIn;
import CodeGeneration.Building.Statements.MyChannelDeclarations.BlockChannelDeclarationOut;
import CodeGeneration.Building.Statements.Selectors.Selector;
import CodeGeneration.Building.Statements.VariableDeclarations.BlockDeclaration;
import CodeGeneration.DataFlow.Network.Nodes.SignalNodes.Channels.ListChannel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

class SimpleCodeScopeTest {

    // Fields:
    private SimpleCodeScope codeScope;

    // Constant:
    private static final String DEFAULT_ID = "id";

    @BeforeEach
    void beforeEach() {
        codeScope = new SimpleCodeScope(DEFAULT_ID);
    }

    @Test
    void getId() {
        assertEquals(codeScope.getId(), DEFAULT_ID);
    }

    @Test
    void getStatementList() {
        Statement in = new BlockChannelDeclarationIn("in");
        Statement out = new BlockChannelDeclarationOut("out");
        this.codeScope.addStatement(in);
        this.codeScope.addStatement(out);

        Collection<Statement> statements = this.codeScope.getStatementList();

        assertEquals(2, statements.size());
        assertTrue(statements.contains(in));
        assertTrue(statements.contains(out));
    }

    @Test
    void getParameterList() {
        BlockDeclaration blockDeclaration = new BlockDeclaration(DEFAULT_ID);
        this.codeScope.addParameter(blockDeclaration);
        assertTrue(this.codeScope.getParameterList().contains(blockDeclaration));
    }

    @Test
    void toCallParameters01() {
        assertNotNull(this.codeScope.toCallParameters());
    }

    @Test
    void toCallParameters02() {
        assertEquals("()", this.codeScope.toCallParameters());
    }

    @Test
    void setId() {
        this.codeScope.setId(DEFAULT_ID);
        assertEquals(DEFAULT_ID, this.codeScope.getId());
    }

    @Test
    void addStatement() {
        Selector selector = new Selector(DEFAULT_ID);
        this.codeScope.addStatement(selector);
        assertTrue(this.codeScope.getStatementList().contains(selector));
    }

    @Test
    void addParameter() {
        BlockDeclaration blockDeclaration = new BlockDeclaration(DEFAULT_ID);
        this.codeScope.addParameter(blockDeclaration);
        assertTrue(this.codeScope.getParameterList().contains(blockDeclaration));
    }

    @Test
    void getParameters01() {
        assertNotNull(this.codeScope.getParameters());
    }

    @Test
    void getParameters02() {
        BlockDeclaration blockDeclaration = new BlockDeclaration(DEFAULT_ID);
        this.codeScope.getParameters().addParameter(blockDeclaration);
        assertTrue(this.codeScope.getParameters().getParameterList().contains(blockDeclaration));
    }

    @Test
    void getStatements() {
        assertNotNull(this.codeScope.getStatements());
    }

    @Test
    void toString01() {
        assertNotNull(this.codeScope.toString());
    }

    @Test
    void toString02() {
        assertTrue(this.codeScope.toString().startsWith("private void"));
    }
}