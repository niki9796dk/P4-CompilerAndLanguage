package CodeGeneration.Building.CodeScopes;

import CodeGeneration.Building.Statement;
import CodeGeneration.Building.Statements.MyChannelDeclarations.BlockChannelDeclarationIn;
import CodeGeneration.Building.Statements.MyChannelDeclarations.BlockChannelDeclarationOut;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

class SimpleCodeScopeTest {

    SimpleCodeScope codeScope;

    @BeforeEach
    void beforeEach() {
        codeScope = new SimpleCodeScope("id");
    }

    @Test
    void getId() {
        assertEquals(codeScope.getId(), "id");
    }

    @Test
    void getStatementList() {
        Statement in = new BlockChannelDeclarationIn("in");
        Statement out = new BlockChannelDeclarationOut("out");
        codeScope.addStatement(in);
        codeScope.addStatement(out);

        Collection<Statement> statements = codeScope.getStatementList();

        assertEquals(2, statements.size());
        assertTrue(statements.contains(in));
        assertTrue(statements.contains(out));
    }

    @Test
    void getParameterList() {
        //Todo: Missing
    }

    @Test
    void setId() {
    }

    @Test
    void addStatement() {
    }

    @Test
    void addParameter() {
    }

    @Test
    void getParameters() {
    }

    @Test
    void getStatements() {
    }

    @Test
    void toString1() {
    }
}