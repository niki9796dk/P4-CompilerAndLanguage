package CodeGeneration.Building.Statements.MyChannelDeclarations;

public class BlockChannelDeclarationIn extends BlockChannelDeclaration {
    public BlockChannelDeclarationIn(String channelId) {
        this.channelId = channelId;
    }

    @Override
    public String toString() {
        return "this.addNewInputLabel" + super.toString();
    }
}
