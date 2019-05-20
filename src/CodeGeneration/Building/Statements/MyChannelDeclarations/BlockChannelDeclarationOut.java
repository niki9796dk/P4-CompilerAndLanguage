package CodeGeneration.Building.Statements.MyChannelDeclarations;

public class BlockChannelDeclarationOut extends BlockChannelDeclaration {
    public BlockChannelDeclarationOut(String channelId) {
        this.channelId = channelId;
    }

    @Override
    public String toString() {
        return "this.addNewOutputLabel" + super.toString();
    }
}
