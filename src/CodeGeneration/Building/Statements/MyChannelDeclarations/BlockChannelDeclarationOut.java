package CodeGeneration.Building.Statements.MyChannelDeclarations;

public class BlockChannelDeclarationOut extends BlockChannelDeclaration {

    // Constant:
    private static final String ADD_NEW_OUTPUT_LABEL = "this.addNewOutputLabel";

    // Constructor:
    public BlockChannelDeclarationOut(String channelId) {
        this.channelId = channelId;
    }

    @Override
    public String toString() {
        return ADD_NEW_OUTPUT_LABEL + super.toString();
    }
}
