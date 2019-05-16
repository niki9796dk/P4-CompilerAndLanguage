package CodeGeneration.Building.Statements.VariableDeclarations;

import CodeGeneration.Building.Statement;
import CodeGeneration.Building.Statements.VariableDeclarations.Enums.JavaTypes;

public abstract class VariableDeclaration implements Statement {
    private JavaTypes type;
    private String identifier;

    public VariableDeclaration(JavaTypes type, String identifier) {
        this.type = type;
        this.identifier = identifier;
    }

    @Override
    public String toString() {
        return type + " " + identifier;
    }
}
