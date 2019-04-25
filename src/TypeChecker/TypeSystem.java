package TypeChecker;

import AST.Enums.NodeEnum;
import AST.Nodes.AbstractNodes.Nodes.AbstractNode;
import AST.Nodes.AbstractNodes.Nodes.AbstractNodes.NumberedNode;
import AST.Nodes.AbstractNodes.Nodes.AbstractNodes.NumberedNodes.NamedNode;
import AST.Nodes.AbstractNodes.Nodes.AbstractNodes.NumberedNodes.NamedNodes.NamedIdNode;
import SymbolTableImplementation.BlockScope;
import SymbolTableImplementation.SymbolTable;

public class TypeSystem {
    private SymbolTable symbolTable;

    TypeSystem(SymbolTable symbolTable) {
        this.symbolTable = symbolTable;
    }

    public NodeEnum getTypeOfNode(AbstractNode node, String blockScopeId, String subScopeId) {
        NumberedNode numberedNode = (NumberedNode) node;

        switch (numberedNode.getNodeEnum()) {
            case ROOT:
            case CHAIN:
            case PROCEDURE_CALL:
            case PARAMS:
            case BLOCK:
            case BLUEPRINT:
            case PROCEDURE:
            case CHANNEL_DECLARATIONS:
                break;

            case CHANNEL_IN:
            case CHANNEL_OUT:
            case SIZE_TYPE:
            case BLOCK_TYPE:
            case SOURCE_TYPE:
            case BLUEPRINT_TYPE:
            case OPERATION_TYPE:
            case SIZE:
                return numberedNode.getNodeEnum();

            case DRAW:
                return NodeEnum.BLUEPRINT_TYPE;

            case BUILD:
                return NodeEnum.BLOCK_TYPE;

            case ASSIGN:
                return this.getTypeOfNode(numberedNode.getChild(), blockScopeId, subScopeId);

            case SELECTOR:
                NamedIdNode namedIdNode = (NamedIdNode) node.getChild();

                BlockScope blockScope = this.symbolTable.getBlockScope(blockScopeId);

                return blockScope
                        .getScope()
                        .getEntry(subScopeId)
                        .getVariable(namedIdNode.getId())
                        .getSuperType();

            default:
                throw new RuntimeException("Unexpected Node");
        }

        return null;
    }
}
