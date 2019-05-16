package CodeGeneration.Building.Statements.VariableDeclarations;

import CodeGeneration.Building.Statements.VariableDeclarations.Enums.JavaTypes;

public class OperationDeclaration extends VariableDeclaration {
    public OperationDeclaration(String identifier) {
        super(JavaTypes.OPERATION, identifier);
    }
}
