package CodeGeneration.Building.Statements.MyChannelDeclarations;

public class BlockChannelDeclarationIn extends BlockChannelDeclaration {

    // Constant:
    private static final String ADD_NEW_INPUT_LABEL = "this.addNewInputLabel";

    // Constructor:
    public BlockChannelDeclarationIn(String channelId) {
        this.channelId = channelId;
    }

    @Override
    public String toString() {
        return ADD_NEW_INPUT_LABEL + super.toString();
    }
}
