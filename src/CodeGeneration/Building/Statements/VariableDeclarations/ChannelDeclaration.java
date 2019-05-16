package CodeGeneration.Building.Statements.VariableDeclarations;

import AST.Nodes.NodeClasses.NamedNodes.NamedIdNodes.InChannelTypeNode;
import AST.Nodes.NodeClasses.NamedNodes.NamedIdNodes.MyInChannelNode;
import AST.Nodes.NodeClasses.NamedNodes.NamedIdNodes.MyOutChannelNode;
import AST.Nodes.NodeClasses.NamedNodes.NamedIdNodes.OutChannelTypeNode;
import CodeGeneration.Building.Statements.VariableDeclarations.Enums.JavaTypes;

public class ChannelDeclaration extends VariableDeclaration {
    public ChannelDeclaration(String identifier) {
        super(JavaTypes.CHANNEL, identifier);
    }
}
