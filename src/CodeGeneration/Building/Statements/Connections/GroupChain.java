package CodeGeneration.Building.Statements.Connections;

import CodeGeneration.Building.Statement;
import CodeGeneration.Building.Statements.Calls.CallParams;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GroupChain implements Statement {
    private List<Statement> groupElements;
    private Statement rightNode;

    public GroupChain(Statement rightNode, List<Statement> groupElements) {
        this.rightNode = rightNode;
        this.groupElements = groupElements;
    }

    public GroupChain(Statement rightNode, Statement ... groupElements) {
        this(rightNode, new ArrayList<>(Arrays.asList(groupElements)));
    }

    private String translateGroup() {
        return new CallParams(this.groupElements).toString();
    }

    @Override
    public String toString() {
        return rightNode + ".receiveGroupConnection" + this.translateGroup(); // TODO: FIX
    }
}
