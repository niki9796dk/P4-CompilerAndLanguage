package CodeGeneration.Building.Statements.Connections;

import CodeGeneration.Building.Statement;
import CodeGeneration.Building.Statements.Calls.CallParams;
import SymbolTableImplementation.Scope;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GroupChain implements Statement {

    // Fields:
    private List<Statement> groupElements;
    private Statement rightNode;
    private Scope scope;

    // Constructors:
    public GroupChain(Scope scope, Statement rightNode, List<Statement> groupElements) {
        this.scope = scope;
        this.rightNode = rightNode;
        this.groupElements = groupElements;
    }

    public GroupChain(Scope scope, Statement rightNode, Statement ... groupElements) {
        this(scope, rightNode, new ArrayList<>(Arrays.asList(groupElements)));
    }

    private String translateGroup() {
        return new CallParams(this.scope, this.groupElements).toString();
    }

    @Override
    public String toString() {
        return rightNode + ".receiveGroupConnection" + this.translateGroup(); // TODO: FIX
    }
}
