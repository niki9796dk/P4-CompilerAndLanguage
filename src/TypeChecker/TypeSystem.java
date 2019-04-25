package TypeChecker;

import AST.Enums.NodeEnum;
import AST.Nodes.AbstractNodes.Nodes.AbstractNode;
import AST.Nodes.AbstractNodes.Nodes.AbstractNodes.NumberedNode;
import AST.Nodes.AbstractNodes.Nodes.AbstractNodes.NumberedNodes.NamedNodes.NamedIdNode;
import SymbolTableImplementation.BlockScope;
import SymbolTableImplementation.SymbolTable;
import SymbolTableImplementation.SymbolTableInterface;

public class TypeSystem {
    private SymbolTableInterface symbolTable;

    public TypeSystem(SymbolTableInterface symbolTable) {
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
                return null; // These nodes don't have a type.

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
                return this.getTypeOfSelector(node, blockScopeId, subScopeId);

            default:
                throw new RuntimeException("Unexpected Node");
        }
    }

    private NodeEnum getTypeOfSelector(AbstractNode node, String blockScopeId, String subScopeId) {
        // Typecast the node to a namedIdNode
        NamedIdNode namedIdNode = (NamedIdNode) node;

        // Check if it's a 'this' selector, and then extract the correct identifier.
        boolean isThis = "this".equals(namedIdNode.getId());
        String identifier = isThis ? ((NamedIdNode) namedIdNode.getChild()).getId() : namedIdNode.getId();

        // Try to get the variable type from both global(Channels) and local(SubScope)
        NodeEnum typeGlobal = getTypeFromGlobal(identifier, blockScopeId);
        NodeEnum typeLocal = getTypeFromLocal(identifier, blockScopeId, subScopeId);

        // Check the types in the correct order, depending on if it's a 'this' selector
        NodeEnum type;
        if (isThis) {
            type = (typeGlobal != null) ? typeGlobal : typeLocal;
        } else {
            type = (typeLocal != null) ? typeLocal : typeGlobal;
        }

        if (type != null) {
            // Return the type, if the identifier is defined.
            return type;
        } else {
            // If the type is null, there is no such identifier defined... Which should have been caught in the scope checking!!!
            throw new RuntimeException("Identifier not defined! - THIS ERROR SHOULD HAVE BEEN DETECTED IN SCOPE CHECKING AND NOT TYPE CHECKING");
        }
    }

    private NodeEnum getTypeFromGlobal(String identifier, String blockScopeId) {
        return this.symbolTable.getSubScope(blockScopeId, BlockScope.CHANNELS).getVariable(identifier).getSuperType();
    }

    private NodeEnum getTypeFromLocal(String identifier, String blockScopeId, String subScopeId) {
        return this.symbolTable.getSubScope(blockScopeId, subScopeId).getVariable(identifier).getSuperType();
    }
}
