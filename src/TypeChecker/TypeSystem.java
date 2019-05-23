package TypeChecker;

import AST.Enums.NodeEnum;
import AST.Nodes.AbstractNodes.Nodes.AbstractNode;
import AST.Nodes.AbstractNodes.Nodes.AbstractNodes.NumberedNode;
import AST.Nodes.AbstractNodes.Nodes.AbstractNodes.NumberedNodes.NamedNode;
import AST.Nodes.AbstractNodes.Nodes.AbstractNodes.NumberedNodes.NamedNodes.NamedIdNode;
import AST.Nodes.NodeClasses.NamedNodes.NamedIdNodes.BlockNode;
import AST.Nodes.NodeClasses.NamedNodes.NamedIdNodes.BuildNode;
import AST.Nodes.NodeClasses.NamedNodes.NamedIdNodes.ProcedureNode;
import AST.Nodes.NodeClasses.NamedNodes.NamedIdNodes.SelectorNode;
import AST.TreeWalks.Exceptions.UnexpectedNodeException;
import SemanticAnalysis.Datastructures.ModeEnum;
import SymbolTableImplementation.*;
import TypeChecker.Exceptions.IncorrectAssignmentTypesException;
import TypeChecker.Exceptions.ParamsTypeInconsistencyException;
import TypeChecker.Exceptions.ShouldNotHappenException;
import TypeChecker.Exceptions.TypeInconsistencyException;

public class TypeSystem {
    private SymbolTableInterface symbolTable;

    public TypeSystem(SymbolTableInterface symbolTable) {
        this.symbolTable = symbolTable;
    }

    /**
     * Returns the symbol table within the type system
     * @return (SymbolTableInterface) A SymbolTableInterface object.
     */
    public SymbolTableInterface getSymbolTable() {
        return symbolTable;
    }

    /**
     * Get's a procedure node from the symbol table
     * @param blockScope The current block scope to fetch the procedure from
     * @param procedure The procedure ID
     * @return (ProcedureNode) A ProcedureNode
     */
    public ProcedureNode getProcedure(String blockScope, String procedure) {
        return (ProcedureNode)
                this.symbolTable
                .getBlockScope(blockScope)
                .getProcedureScope(procedure)
                .getNode();
    }

    /**
     * Get's a block node from the symbol table
     * @param blockId The block scope ID.
     * @return (BlockNode) A BlockNode from the symbol table.
     */
    public BlockNode getBlock(String blockId) {
        return (BlockNode)
                this.symbolTable
                .getBlockScope(blockId)
                .getNode();
    }

    /**
     * Asserts that two nodes have the same type, and throws an exception if this condition is not held.
     * @param leftNode The first node to compare
     * @param rightNode The second node to compare
     * @param currentBlockScope The current block scope to type check from
     * @param currentSubScope The current sub scope to type check from
     */
    public void assertEqualSuperTypes(AbstractNode leftNode, AbstractNode rightNode, String currentBlockScope, String currentSubScope) {
        try {
            this.assertEqualSuperTypes(leftNode, rightNode, currentBlockScope, currentSubScope, "Different type on the left and right side node");
        } catch (TypeInconsistencyException e) {
            throw new IncorrectAssignmentTypesException(e);
        }
    }

    /**
     * Asserts that two nodes have the same type, and throws an exception if this condition is not held.
     * @param leftNode The first node to compare
     * @param rightNode The second node to compare
     * @param currentBlockScope The current block scope to type check from
     * @param currentSubScope The current sub scope to type check from
     * @param errorMsgPrefix The error msg prefix.
     */
    public void assertEqualSuperTypes(AbstractNode leftNode, AbstractNode rightNode, String currentBlockScope, String currentSubScope, String errorMsgPrefix) {
        // Get the types of both left and right node.
        NodeEnum leftNodeType = this.getSuperTypeOfNode(leftNode, currentBlockScope, currentSubScope);
        NodeEnum rightNodeType = this.getSuperTypeOfNode(rightNode, currentBlockScope, currentSubScope);

        // Then compare them.
        if (leftNodeType != rightNodeType) {
            throw new ParamsTypeInconsistencyException(errorMsgPrefix + ": " + leftNode + "("+ leftNodeType +") = " + rightNode + "("+ rightNodeType +")");
        }
    }


    public String getSubTypeOfNode(AbstractNode node, String blockScopeId, String subScopeId) {
        NumberedNode numberedNode = (NumberedNode) node;
        NamedIdNode namedIdNode = (node instanceof NamedIdNode) ? (NamedIdNode) node : null;

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
                return null; // These nodes don't have a sub type.

            case SIZE_TYPE:
            case BLOCK_TYPE:
            case SOURCE_TYPE:
            case BLUEPRINT_TYPE:
            case OPERATION_TYPE:
                return null; // These nodes, while they might have super type, would never actually have a sub type, since they are declarations.

            case CHANNEL_IN_MY:
            case CHANNEL_OUT_MY:
            case CHANNEL_OUT_TYPE:
            case CHANNEL_IN_TYPE:
                return numberedNode.getNodeEnum().toString();

            case SIZE:
                return NodeEnum.SIZE_TYPE.toString();

            case DRAW:
                return namedIdNode.getId();

            case BUILD:
            case ASSIGN:
                return ((NamedIdNode) this.followNode(node, blockScopeId, subScopeId)).getId();
            case SELECTOR:
                AbstractNode base = this.followNode(node, blockScopeId, subScopeId);
                if (base instanceof SelectorNode){
                    return this.getSuperTypeOfNode(base, blockScopeId, subScopeId).toString();
                } else {
                    return ((NamedIdNode) base).getId();
                }

            default:
                throw new UnexpectedNodeException(numberedNode.getNodeEnum());
        }
    }

    public AbstractNode followNode(AbstractNode node, String blockScopeId, String subScopeId){
        return followNode(node, blockScopeId, subScopeId, ModeEnum.DEFAULT);
    }

    public AbstractNode followNodeToBuild(AbstractNode node, String blockScopeId, String subScopeId){
        return followNode(node, blockScopeId, subScopeId, ModeEnum.BLOCK_SEEKER);
    }

    public AbstractNode followNodeToBuildOfChannel(AbstractNode node, String blockScopeId, String subScopeId){
        return followNode(node, blockScopeId, subScopeId, ModeEnum.CHANNEL_INSTANCE);
    }

    /**
     * @param node The node to evaluate
     * @param blockScopeId The current block scope to type check from
     * @param subScopeId The current sub scope to type check from
    * @param subScopeId The current sub scope to type check from
     */
    private AbstractNode followNode(AbstractNode node, String blockScopeId, String subScopeId, ModeEnum mode){
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
                return null; // These nodes don't have a sub type.

            case SIZE_TYPE:
            case BLOCK_TYPE:
            case SOURCE_TYPE:
            case BLUEPRINT_TYPE:
            case OPERATION_TYPE:
                return null; // These nodes, while they might have super type, would never actually have a sub type, since they are declarations.

            case CHANNEL_IN_MY:
            case CHANNEL_OUT_MY:
            case CHANNEL_OUT_TYPE:
            case CHANNEL_IN_TYPE:
                return node;

            case SIZE:
                return node;

            case DRAW:
                return node;

            case BUILD:
                if (mode.equals(ModeEnum.BLOCK_SEEKER) || mode.equals(ModeEnum.CHANNEL_INSTANCE)){
                    return node;
                } else {
                    return this.getOriginOfBuildStatement((BuildNode) node, blockScopeId, subScopeId, mode);
                }
            case ASSIGN:
                return this.followNode(numberedNode.getChild().getSib(), blockScopeId, subScopeId, mode); // TODO: Maybe rethink this... Since an assignment dont really have a type? Does it?

            case SELECTOR:
                return this.followSelector((SelectorNode) node, blockScopeId, subScopeId, mode);
                // returns a selectornode in dot.notation cases

            default:
                throw new UnexpectedNodeException(numberedNode.getNodeEnum());
        }
    }

    /**
     * Gets and returns the sub type of a given build statement.
     * @param node The build node
     * @param blockScopeId The current block scope, from which we will compare from
     * @param subScopeId The current sub scope, from which we will compare from
     * @return The sub type of the build statement.
     */
    private AbstractNode getOriginOfBuildStatement(BuildNode node, String blockScopeId, String subScopeId, ModeEnum mode) {
        String buildId = node.getId();

        boolean isPredefinedOperation = this.symbolTable.isPredefinedOperation(buildId);
        boolean isPredefinedSource = this.symbolTable.isPredefinedSource(buildId);
        boolean isLocalVariable = this.getVariableFromIdentifier(buildId, blockScopeId, subScopeId) != null;
        boolean isDefinedBlock = this.symbolTable.getBlockScope(buildId) != null;

        // The order here is relevant! We should always check the local scope before the predefined and the set of block scopes!
        if (isLocalVariable) {
            // Convert the identifier into an selector
            SelectorNode selectorNode = new SelectorNode(buildId);
            selectorNode.setNumber(node.getNumber());

            // Get the sub type of the selector
            return this.followNode(selectorNode, blockScopeId, subScopeId, mode);

        } else if (isPredefinedOperation || isPredefinedSource) {
            return node;

        } else if (isDefinedBlock) {
            return node;

        } else {
            throw new ShouldNotHappenException("We are building something which is not defined? Should have been caught in the scope checker! - " + node +  " : " + buildId);
        }
    }

    /**
     * Returns the sub type of a SelectorNode
     * @param selectorNode The selector node from which the sub type should be extracted
     * @param blockScope The current block scope of the node
     * @param subScope The current sub scope of then node
     * @return The subtype as a string.
     */
    private AbstractNode followSelector(SelectorNode selectorNode, String blockScope, String subScope, ModeEnum mode) {
        boolean hasSelectorChild = selectorNode.getChild() instanceof SelectorNode;
        boolean isThis = "this".equals(selectorNode.getId());

        if (isThis || hasSelectorChild) {
            return selectorNode;

        } else {
            NamedIdNode subtypeNode;
            VariableEntry selectorVariable = this.getVariableFromIdentifier(selectorNode.getId(), blockScope, subScope);

            //


            if (selectorVariable != null){
                subtypeNode = selectorVariable.getSubType(selectorNode.getNumber());

                // If we are specifically looking for the origin of a channel variable, and we just reached a myChannel, the selector we just checked is the parameter variable that we need.
                if (mode.equals(ModeEnum.CHANNEL_INSTANCE)){
                    if (subtypeNode.getNodeEnum().equals(NodeEnum.CHANNEL_IN_MY) || subtypeNode.getNodeEnum().equals(NodeEnum.CHANNEL_OUT_MY)){
                        return selectorNode;
                    }
                }

            } else {
                // Handle this.-less references to channels
                subtypeNode = symbolTable.getBlockScope(blockScope).getChannelDeclarationScope().getVariable(selectorNode).getNode();
            }

            return this.followNode(subtypeNode, blockScope, subScope, mode);

            /*
            if (subtypeNode instanceof SelectorNode) {
                return this.getSubTypeOfSelector((SelectorNode) subtypeNode, blockScope, subScope);
            } else {
                return subtypeNode.getId();
            }
            */
        }
    }

    /**
     * Evaluates the super type of a given node, and returns that type in the form of a NodeEnum.
     * @param node The node to evaluate
     * @param blockScopeId The current block scope to type check from
     * @param subScopeId The current sub scope to type check from
     * @return (NodeEnum|null) The type of the given node, as a NodeEnum. Returns null, if the given node does not have a designated type.
     */
    public NodeEnum getSuperTypeOfNode(AbstractNode node, String blockScopeId, String subScopeId) {
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

            case CHANNEL_IN_MY:
            case CHANNEL_OUT_MY:
            case SIZE_TYPE:
            case BLOCK_TYPE:
            case SOURCE_TYPE:
            case BLUEPRINT_TYPE:
            case OPERATION_TYPE:
            case CHANNEL_OUT_TYPE:
            case CHANNEL_IN_TYPE:
                return numberedNode.getNodeEnum();

            case SIZE:
                return NodeEnum.SIZE_TYPE;

            case DRAW:
                return NodeEnum.BLUEPRINT_TYPE;

            case BUILD:
                return this.getTypeOfBuildStatement(node, blockScopeId, subScopeId);

            case ASSIGN:
                return this.getSuperTypeOfNode(numberedNode.getChild(), blockScopeId, subScopeId); // TODO: Maybe rethink this... Since an assignment dont really have a type? Does it?

            case SELECTOR:
                return this.getSuperTypeOfSelector(node, blockScopeId, subScopeId);

            default:
                throw new UnexpectedNodeException(numberedNode.getNodeEnum());
        }
    }

    /**
     * Returns the type of a build statement. It will check weather the build ID, is a operation, source or a block - In that order.
     * @param node The build node to check
     * @return (NodeEnum) The type of the build statement
     */
    private NodeEnum getTypeOfBuildStatement(AbstractNode node, String blockScope, String subScope) {
        String id = this.getIdFromNode(node);

        if (this.symbolTable.isPredefinedOperation(id)) {
            return NodeEnum.OPERATION_TYPE;

        } else if (this.symbolTable.isPredefinedSource(id)) {
            return NodeEnum.SOURCE_TYPE;

        } else {
            Scope scope = this.symbolTable.getSubScope(blockScope, subScope);
            VariableEntry localVariable = scope.getVariable(node);

            boolean isLocalVariable = localVariable != null;

            if (isLocalVariable) {
                int nodeNumber = ((NumberedNode) node).getNumber();
                String variableId = localVariable.getSubType(nodeNumber).getId();

                BuildNode varBuild = new BuildNode(variableId);
                varBuild.setNumber(nodeNumber);

                return this.getTypeOfBuildStatement(varBuild, blockScope, subScope);
            } else {
                String subType = this.getSubTypeOfNode(node, blockScope, subScope);

                if (this.getBlock(subType) != null) {
                    return NodeEnum.BLOCK_TYPE;
                } else {
                    throw new ShouldNotHappenException("We are building an undefined block/blueprint thingy - " + node);
                }
            }
        }
    }

    /**
     * Evaluates the type of a selector statement, and returns the type of whatever the selector is pointing to.
     * @param node The SelectorNode to evaluate
     * @param blockScopeId The current block scope to evaluate from
     * @param subScopeId The current sub scope to evaluate from
     * @return (NodeEnum|null) The type of node, the selector is pointing at.
     */
    private NodeEnum getSuperTypeOfSelector(AbstractNode node, String blockScopeId, String subScopeId) {
        String nodeId = this.getIdFromNode(node);

        // Evaluate booleans
        boolean isThis = "this".equals(nodeId);
        boolean isChildless = node.getChild() == null;
        boolean isNotChildOfSelector = !(node.getParent() instanceof SelectorNode);

        if (isThis || isChildless) {
            return this.getTypeOfSelectorVariable(node, blockScopeId, subScopeId);

        } else if (isNotChildOfSelector) {
            VariableEntry variable = this.getVariableFromIdentifier(nodeId, blockScopeId, subScopeId);

            String value = this.getSubTypeOfNode(variable.getSubType(this.getNumberFromNode(node)), blockScopeId, subScopeId);
            String childId = this.getIdFromNode(node.getChild());

            NodeEnum superType = variable.getSuperType();

            switch (superType) {
                case BLOCK_TYPE:
                    // Extract the sub scope, and assert that it's not null.
                    Scope subScope = this.symbolTable.getSubScope(value, BlockScope.CHANNELS);
                    this.assertNotNull(subScope, "No such block defined '" + value + "' - " + node);

                    // Extract the variable, and assert that it's not null.
                    VariableEntry variableEntryBlock = subScope.getVariable(childId);
                    this.assertNotNull(variableEntryBlock, "No such channel defined '" + childId + "' - " + node.getChild());

                    // Get the super type from the variable
                    NodeEnum type = variableEntryBlock.getSuperType();

                    // Translate external channels and return the translated type.
                    return this.translateExternalChannelTypes(type);

                case OPERATION_TYPE:
                    // TODO: Somehow include the operations in the symbol table instead of this BS.
                    if ("A".equals(childId) || "B".equals(childId)) {
                        return NodeEnum.CHANNEL_IN_TYPE;

                    } else if ("out".equals(childId)) {
                        return NodeEnum.CHANNEL_OUT_TYPE;

                    } else {
                        // SHOULD NOT HAPPEN HERE!!! THIS SHOULD HAVE BEEN CAUGHT IN THE SCOPE CHECKING
                        throw new ShouldNotHappenException("The operation '" + value + "' does not have a channel named '" + childId + "'");
                    }

            }

            return null;

        } else {
            // Should not happen with normal calling behaviour.
            return null;
        }
    }

    /**
     * Helper function, for asserting that an object is not null, and throw an exception if it is.
     * @param object The object to evaluate
     * @param errorMsg The error messages to throw if the object is null.
     * @throws ShouldNotHappenException Is thrown if the object is null.
     */
    private void assertNotNull(Object object, String errorMsg) {
        if (object == null) {
            throw new ShouldNotHappenException(errorMsg);
        }
    }

    /**
     * Translates the super type of an external channel into the correct internal channel type.
     * eg. mychannel:in -> channel:in
     * @param type The type of the external channel
     * @return The translated super type.
     */
    private NodeEnum translateExternalChannelTypes(NodeEnum type) {
        switch (type) {
            case CHANNEL_IN_MY:
                return NodeEnum.CHANNEL_IN_TYPE;
            case CHANNEL_OUT_MY:
                return NodeEnum.CHANNEL_OUT_TYPE;

            default:
                return type;
        }
    }

    /**
     * Returns the type of a selector, which is known to point at a local variable and not anything else (eg. procedures, mychannels...)
     * @param node The selector node to evaluate
     * @param blockScopeId The current block scope to evaluate from
     * @param subScopeId The current sub scope to evaluate from
     * @return (NodeEnum) The type of the local variable that the selector is pointing at.
     */
    private NodeEnum getTypeOfSelectorVariable(AbstractNode node, String blockScopeId, String subScopeId) {
        String nodeId = this.getIdFromNode(node);

        // Check if it's a 'this' selector, and then extract the correct identifier.
        boolean isThis = "this".equals(nodeId);
        String identifier = isThis ? this.getIdFromNode(node.getChild()) : nodeId;

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
            throw new ShouldNotHappenException("Identifier not defined: " + (isThis ? ("this." + node.getChild()) : node) + " - THIS ERROR SHOULD HAVE BEEN DETECTED IN SCOPE CHECKING AND NOT TYPE CHECKING");
        }
    }

    /**
     * Typecasts an abstract node to an NamedIdNode, and then returns the id from that node.
     * @param abstractNode The node to extract the ID from.
     * @return (String) The id of the node
     */
    private String getIdFromNode(AbstractNode abstractNode) {
        return ((NamedIdNode) abstractNode).getId();
    }

    /**
     * Typecasts an abstract node to an NumberedNode, and then returns the number from that node.
     * @param abstractNode The node to extract the number from.
     * @return (int) The number of the node
     */
    private int getNumberFromNode(AbstractNode abstractNode) {
        return ((NumberedNode) abstractNode).getNumber();
    }

    /**
     * Returns the super type of a variable from the "global" scope, which is the channel declaration scope
     * @param identifier The variable identifier to select
     * @param blockScopeId The current block scope to fetch from
     * @return (NodeEnum|null) Returns the super type of a variable within the "global" scope of the block. Returns null if the variable does not exist in the scope.
     */
    private NodeEnum getTypeFromGlobal(String identifier, String blockScopeId) {
        return this.getType(identifier, blockScopeId, BlockScope.CHANNELS);
    }

    /**
     Returns the super type of a variable from the "local" scope, which is the current sub scope.
     * @param identifier The variable identifier to select
     * @param blockScopeId The current block scope to fetch from
     * @param subScopeId The current sub scope to fetch from
     * @return (NodeEnum|null) Returns the super type of a variable within the "local" scope. Returns null if the variable does not exist in the scope.
     */
    private NodeEnum getTypeFromLocal(String identifier, String blockScopeId, String subScopeId) {
        return this.getType(identifier, blockScopeId, subScopeId);
    }

    /**
     * Returns the super type of a variable in a given block scope and sub scope.
     * @param identifier The variable identifier to select
     * @param blockScopeId The current block scope to fetch from
     * @param subScopeId The current sub scope to fetch from
     * @return (NodeEnum|null) Returns the super type of a variable within the selected sub scope. Returns null if the variable does not exist in the sub scope.
     */
    private NodeEnum getType(String identifier, String blockScopeId, String subScopeId) {
        VariableEntry variable = this.getVariableFromIdentifier(identifier, blockScopeId, subScopeId);
        return variable != null ? variable.getSuperType() : null;
    }

    /**
     * Returns the variable from a given block and sub scope from an identifier
     * @param identifier The variable identifier
     * @param blockScopeId The current block scope to fetch from
     * @param subScopeId The current sub scope to fetch from
     * @return (NodeEnum|null) Returns the variable from the selector in the given scope. Returns null if the variable does not exist.
     */
    private VariableEntry getVariableFromIdentifier(String identifier, String blockScopeId, String subScopeId) {
        return this.symbolTable
                .getSubScope(blockScopeId, subScopeId)
                .getVariable(identifier);
    }
}
