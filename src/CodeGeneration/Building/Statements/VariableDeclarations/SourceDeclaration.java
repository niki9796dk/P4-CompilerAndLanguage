package CodeGeneration.Building.Statements.VariableDeclarations;

import CodeGeneration.Building.Parameter;
import CodeGeneration.Building.Statements.VariableDeclarations.Enums.JavaTypes;

public class SourceDeclaration extends VariableDeclaration implements Parameter {
    public SourceDeclaration(String identifier) {
        super(JavaTypes.SOURCE, identifier);
    }
}
