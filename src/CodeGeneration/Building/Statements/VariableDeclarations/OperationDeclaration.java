package CodeGeneration.Building.Statements.VariableDeclarations;

import CodeGeneration.Building.Parameter;
import CodeGeneration.Building.Statements.VariableDeclarations.Enums.JavaTypes;

public class OperationDeclaration extends VariableDeclaration implements Parameter {
    public OperationDeclaration(String identifier) {
        super(JavaTypes.OPERATION, identifier);
    }
}
