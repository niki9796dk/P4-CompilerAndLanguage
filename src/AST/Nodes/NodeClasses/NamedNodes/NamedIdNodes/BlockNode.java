package AST.Nodes.NodeClasses.NamedNodes.NamedIdNodes;

import AST.Enums.NodeEnum;
import AST.Nodes.AbstractNodes.Nodes.AbstractNodes.NumberedNodes.NamedNodes.NamedIdNode;

import java.util.ArrayList;
import java.util.List;

/**
 * A node representing a "block".
 * It is a child of {@link AST.Nodes.NodeClasses.NamedNodes.RootNode}
 * Parent of a {@link AST.Nodes.NodeClasses.NamedNodes.BlueprintNode}, a {@link AST.Nodes.NodeClasses.NamedNodes.ChannelDeclarationsNode}, and several {@link ProcedureNode}.
 * @see AST.Nodes.NodeClasses.NamedNodes.RootNode
 */
public class BlockNode extends NamedIdNode {

    private List<String> blockchain;

    /**
     * @param id Create a block with the specified node
     */
    public BlockNode(String id) {
        super("Block", id, NodeEnum.BLOCK);
    }

    public BlockNode(String id, List<String> blockchain) {
        super("Block", id, NodeEnum.BLOCK);
        this.blockchain = blockchain;
    }

    public List<String> getBlockchain() {
        List<String> blockchainClone = new ArrayList<>(blockchain);
        blockchainClone.add(this.getId());
        return  blockchainClone;
    }

    @Override
    public String toString() {
        return "\n\n" + super.toString();
    }
}