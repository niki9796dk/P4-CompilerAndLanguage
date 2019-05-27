package CodeGeneration.Building.Statements.VariableDeclarations;

import CodeGeneration.Building.Parameter;
import CodeGeneration.Building.Statements.VariableDeclarations.Enums.JavaTypes;

public class ChannelDeclaration extends VariableDeclaration implements Parameter {
    public ChannelDeclaration(String identifier) {
        super(JavaTypes.CHANNEL, identifier);
    }
}
