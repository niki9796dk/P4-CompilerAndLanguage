package TypeChecker;

import AST.Enums.NodeEnum;
import AST.Nodes.AbstractNodes.Nodes.AbstractNode;
import AST.Nodes.AbstractNodes.Nodes.AbstractNodes.NumberedNode;
import AST.Nodes.AbstractNodes.Nodes.AbstractNodes.NumberedNodes.NamedNodes.NamedIdNode;
import AST.TreeWalks.Exceptions.UnexpectedNodeException;
import SymbolTableImplementation.*;

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
            case GROUP:
                return null; // These nodes don't have a type.

            case CHANNEL_IN:
            case CHANNEL_OUT:
            case CHANNEL_OUT_TYPE:
            case CHANNEL_IN_TYPE:
            case SIZE_TYPE:
            case BLOCK_TYPE:
            case SOURCE_TYPE:
            case BLUEPRINT_TYPE:
            case OPERATION_TYPE:
            case SIZE:
                return numberedNode.getNodeEnum();

                /*
            case GROUP:
                AbstractNode child = node.getChild();
                return this.getTypeOfNode(child, blockScopeId, subScopeId); // TODO: Find a better solution, this is kinda wrong and akward, but works.
                */

            case DRAW:
                return NodeEnum.BLUEPRINT_TYPE;

            case BUILD:
                return NodeEnum.BLOCK_TYPE;

            case ASSIGN:
                return this.getTypeOfNode(numberedNode.getChild(), blockScopeId, subScopeId);

            case SELECTOR:
                return this.getTypeOfSelector(node, blockScopeId, subScopeId);

            default:
                throw new UnexpectedNodeException(numberedNode.getNodeEnum());
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
        Scope subScope = this.symbolTable.getSubScope(blockScopeId, BlockScope.CHANNELS);
        VariableEntry variable = subScope.getVariable(identifier);

        return variable != null ? variable.getSuperType() : null;
    }

    private NodeEnum getTypeFromLocal(String identifier, String blockScopeId, String subScopeId) {
        Scope subScope = this.symbolTable.getSubScope(blockScopeId, subScopeId);
        VariableEntry variable = subScope.getVariable(identifier);

        return variable != null ? variable.getSuperType() : null;
    }
}
