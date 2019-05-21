package CodeGeneration.Building.Statements.VariableDeclarations;

import CodeGeneration.Building.Parameter;
import CodeGeneration.Building.Statements.VariableDeclarations.Enums.JavaTypes;

public class BlueprintDeclaration extends VariableDeclaration implements Parameter {
    public BlueprintDeclaration(String identifier) {
        super(JavaTypes.CLASS, identifier);
    }

}
