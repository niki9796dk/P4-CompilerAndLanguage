package CodeGeneration.Building.Statements.VariableDeclarations;

import AST.Nodes.NodeClasses.NamedNodes.NamedIdNodes.BlockTypeNode;
import CodeGeneration.Building.Parameter;
import CodeGeneration.Building.Statements.VariableDeclarations.Enums.JavaTypes;

public class BlockDeclaration extends VariableDeclaration implements Parameter {
    public BlockDeclaration(String identifier) {
        super(JavaTypes.BLOCK, identifier);
    }

}
