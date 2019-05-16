package CodeGeneration.Building;

import CodeGeneration.Building.Statements.Connections.SingleChain;
import CodeGeneration.Building.Statements.MyChannelDeclarations.BlockChannelDeclarationIn;
import CodeGeneration.Building.Statements.MyChannelDeclarations.BlockChannelDeclarationOut;
import CodeGeneration.Building.Statements.VariableDeclarations.BlockDeclaration;

public class MainTest {
    public static void main(String[] args) {
        BlockClass myBlock = new BlockClass("MyNewBlock", "CodeGeneration.Building");

        myBlock.getBlueprint().addStatement(new BlockChannelDeclarationIn("myInChannel"));
        myBlock.getBlueprint().addStatement(new BlockChannelDeclarationOut("myOutChannel"));

        myBlock.getBlueprint().addStatement(new BlockDeclaration("myBlockVar"));
        myBlock.getBlueprint().addStatement(new SingleChain("this", "myInChannel", "myBlockVar", "A"));

        System.out.println(myBlock);
    }
}
