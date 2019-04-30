package TypeChecker;

import AST.Enums.NodeEnum;
import AST.Nodes.AbstractNodes.Nodes.AbstractNode;
import AST.Nodes.AbstractNodes.Nodes.AbstractNodes.NumberedNode;
import AST.Nodes.AbstractNodes.Nodes.AbstractNodes.NumberedNodes.NamedNodes.NamedIdNode;
import AST.Nodes.NodeClasses.NamedNodes.NamedIdNodes.BlockNode;
import AST.Nodes.NodeClasses.NamedNodes.NamedIdNodes.ProcedureNode;
import AST.Nodes.NodeClasses.NamedNodes.NamedIdNodes.SelectorNode;
import AST.TreeWalks.Exceptions.UnexpectedNodeException;
import SymbolTableImplementation.*;
import TypeChecker.Exceptions.ShouldNotHappenException;

public class TypeSystem {
    private SymbolTableInterface symbolTable;

    public TypeSystem(SymbolTableInterface symbolTable) {
        this.symbolTable = symbolTable;
    }

    public SymbolTableInterface getSymbolTable() {
        return symbolTable;
    }

    public ProcedureNode getProcedure(String blockScope, String procedure) {
        return (ProcedureNode)
                this.symbolTable
                .getBlockScope(blockScope)
                .getProcedureScope(procedure)
                .getNode();
    }

    public BlockNode getBlock(String blockId) {
        return (BlockNode)
                this.symbolTable
                .getBlockScope(blockId)
                .getNode();
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
            case SIZE_TYPE:
            case BLOCK_TYPE:
            case SOURCE_TYPE:
            case BLUEPRINT_TYPE:
            case OPERATION_TYPE:
                return numberedNode.getNodeEnum();

            case CHANNEL_OUT_TYPE:
                return numberedNode.getNodeEnum();
                //return NodeEnum.CHANNEL_OUT;

            case CHANNEL_IN_TYPE:
                return numberedNode.getNodeEnum();
                //return NodeEnum.CHANNEL_IN;

                /*
            case GROUP:
                AbstractNode child = node.getChild();
                return this.getTypeOfNode(child, blockScopeId, subScopeId); // TODO: Find a better solution, this is kinda wrong and akward, but works.
                */

            case SIZE:
                return NodeEnum.SIZE_TYPE;

            case DRAW:
                return NodeEnum.BLUEPRINT_TYPE;

            case BUILD:
                String id = ((NamedIdNode) node).getId();

                if (this.symbolTable.isPredefinedOperation(id)) {
                    return NodeEnum.OPERATION_TYPE;

                } else if (this.symbolTable.isPredefinedSource(id)) {
                    return NodeEnum.SOURCE_TYPE;

                } else {
                    return NodeEnum.BLOCK_TYPE;
                }

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

        // Evaluate booleans
        boolean isThis = "this".equals(namedIdNode.getId());
        boolean isChildless = node.getChild() == null;
        boolean isNotChildOfSelector = !(node.getParent() instanceof SelectorNode);

        if (isThis || isChildless) {
            return this.getTypeOfSelectorVariable(node, blockScopeId, subScopeId);
        } else if (isNotChildOfSelector) {
            String nodeId = namedIdNode.getId();
            int nodeNumber = namedIdNode.getNumber();

            VariableEntry variable = this.symbolTable
                    .getSubScope(blockScopeId, subScopeId)
                    .getVariable(nodeId);

            String variableId = variable.getSubType(nodeNumber).getId();
            String childId = ((NamedIdNode) node.getChild()).getId();

            NodeEnum superType = variable.getSuperType();

            switch (superType) {
                case BLOCK_TYPE:
                    Scope subScope = this.symbolTable.getSubScope(variableId, BlockScope.CHANNELS);

                    if (subScope == null) {
                        throw new ShouldNotHappenException("SHOULD NOT HAPPEN HERE - No such block defined '" + variableId + "' - " + node);
                    }

                    VariableEntry variableEntryBlock = subScope.getVariable(childId);

                    if (variableEntryBlock == null) {
                        throw new ShouldNotHappenException("SHOULD NOT HAPPEN HERE - No such channel defined '" + childId + "' - " + node.getChild());
                    }

                    NodeEnum type = variableEntryBlock.getSuperType();

                    switch (type) {
                        case CHANNEL_IN:
                            return NodeEnum.CHANNEL_IN_TYPE;
                        case CHANNEL_OUT:
                            return NodeEnum.CHANNEL_OUT_TYPE;

                        default:
                            return type;
                    }

                case OPERATION_TYPE:
                    // TODO: Somehow include the operations in the symbol table instead of this BS.
                    if ("A".equals(childId) || "B".equals(childId)) {
                        return NodeEnum.CHANNEL_IN_TYPE;
                    } else if ("out".equals(childId)) {
                        return NodeEnum.CHANNEL_OUT_TYPE;
                    } else {
                        // SHOULD NOT HAPPEN HERE!!! THIS SHOULD HAVE BEEN CAUGHT IN THE SCOPE CHECKING
                        throw new ShouldNotHappenException("SHOULD NOT HAPPEN HERE! - The operation '" + variableId + "' does not have a channel named '" + childId + "'");
                    }

            }

            return null;

        } else {
            // Should not happen with normal calling behaviour.
            return null;
        }
    }

    private NodeEnum getTypeOfSelectorVariable(AbstractNode node, String blockScopeId, String subScopeId) {
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
            type = typeGlobal;
        } else {
            type = (typeLocal != null) ? typeLocal : typeGlobal;
        }

        if (type != null) {
            // Return the type, if the identifier is defined.
            return type;
        } else {
            // If the type is null, there is no such identifier defined... Which should have been caught in the scope checking!!!
            throw new RuntimeException("Identifier not defined: " + (isThis ? ("this." + node.getChild()) : node) + " - THIS ERROR SHOULD HAVE BEEN DETECTED IN SCOPE CHECKING AND NOT TYPE CHECKING");
        }
    }

    private NodeEnum getTypeFromGlobal(String identifier, String blockScopeId) {
        return this.getType(identifier, blockScopeId, BlockScope.CHANNELS);
    }

    private NodeEnum getTypeFromLocal(String identifier, String blockScopeId, String subScopeId) {
        return this.getType(identifier, blockScopeId, subScopeId);
    }

    private NodeEnum getType(String identifier, String blockScopeId, String subScopeId) {
        Scope subScope = this.symbolTable.getSubScope(blockScopeId, subScopeId);
        VariableEntry variable = subScope.getVariable(identifier);

        return variable != null ? variable.getSuperType() : null;
    }
}
