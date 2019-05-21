package CodeGeneration.Building.Statements.VariableDeclarations;

import CodeGeneration.Building.Parameter;
import CodeGeneration.Building.Statements.VariableDeclarations.Enums.JavaTypes;

public class SizeDeclaration extends VariableDeclaration implements Parameter {
    public SizeDeclaration(String identifier) {
        super(JavaTypes.SIZE, identifier);
    }
}
