package CodeGeneration.Building.Statements.Connections;

import CodeGeneration.Building.Statement;

import java.util.List;

public class GroupChain implements Statement {
    private List<Statement> groupElements;

    public GroupChain(List<Statement> groupElements) {
        this.groupElements = groupElements;
    }

    @Override
    public String toString() {
        return "GROUP ===>>>"; // TODO: FIX
    }
}
