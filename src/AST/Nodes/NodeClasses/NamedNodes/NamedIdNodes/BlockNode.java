package AST.Nodes.NodeClasses.NamedNodes.NamedIdNodes;

import AST.Enums.NodeEnum;
import AST.Nodes.AbstractNodes.Nodes.AbstractNodes.NumberedNodes.NamedNodes.NamedIdNode;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BlockNode)) return false;
        BlockNode blockNode = (BlockNode) o;
        return Objects.equals(this.getName(), blockNode.getName())
                && Objects.equals(this.getId(), blockNode.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getName(), this.getId());
    }
}