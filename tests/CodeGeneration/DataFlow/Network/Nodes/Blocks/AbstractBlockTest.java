package CodeGeneration.DataFlow.Network.Nodes.Blocks;



import CodeGeneration.DataFlow.Network.Nodes.Block;
import CodeGeneration.DataFlow.Network.Nodes.BlocksAndSignalNodes.Operations.AbstractOperation;
import CodeGeneration.DataFlow.Network.Nodes.BlocksAndSignalNodes.Operations.BinaryOperations.MatrixOperations.Multiplication;
import CodeGeneration.DataFlow.Network.Nodes.BlocksAndSignalNodes.Operations.BinaryOperations.UnitWiseOperations._Subtraction;
import CodeGeneration.DataFlow.Network.Nodes.SignalNodes.Channel;
import CodeGeneration.DataFlow.Network.Nodes.SignalNodes.Channels.ListChannel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class AbstractBlockTest {


    Block block;

    @BeforeEach
    void beforeEach() {
        block = new testImplementation000();

    }

    @Test
    void hasInputChannel() {
        assertTrue(block.hasInputChannel("A"));
        assertTrue(block.hasInputChannel("B"));
        assertFalse(block.hasInputChannel("C"));
        assertFalse(block.hasInputChannel("a"));
    }

    @Test
    void hasOutputChannel() {
        assertTrue(block.hasOutputChannel("out"));
        assertFalse(block.hasOutputChannel("fkc"));
        assertFalse(block.hasOutputChannel("Out"));
        assertFalse(block.hasOutputChannel("OUT"));
    }

    @Test
    void getChannel() {
        assertNotNull(block.getChannel("A"));

        assertThrows(IllegalArgumentException.class, () -> block.getChannel("C"));
    }

    @Test
    void getInputChannels() {
        assertTrue(block.getInputChannels().containsKey("A"));
        assertTrue(block.getInputChannels().containsKey("B"));
    }

    @Test
    void getOutputChannels() {
        assertTrue(block.getOutputChannels().containsKey("out"));
    }
}

class A_sub_B_mult_B extends AbstractBlock {
    public A_sub_B_mult_B() {
        // Channel
        this.addNewInputLabel("A", new ListChannel());
        this.addNewInputLabel("B", new ListChannel());
        this.addNewOutputLabel("out", new ListChannel());

        // Blueprint
        AbstractOperation sub = new _Subtraction();
        AbstractOperation mult = new Multiplication();

        this.connectTo(sub, "A", "in1");
        this.connectTo(sub, "B", "in2");

        sub.connectTo(mult, "out", "in1");

        this.connectTo(mult, "B", "in2");

        mult.connectTo(this, "out", "out");
    }
}

class testImplementation000 extends AbstractBlock {
    public testImplementation000() {
        // Channel
        Channel aChannel = new ListChannel();
        this.addNewInputLabel("A", aChannel);
        this.addNewInputLabel("B", new ListChannel());

        Channel outChannel = new ListChannel();
        this.addNewOutputLabel("out", outChannel);

        // Blueprint
        AbstractOperation sub = new _Subtraction();
        AbstractOperation mult = new Multiplication();

        this.connectTo(sub, "A", "in1");
        this.connectTo(sub, "B", "in2");

        sub.connectTo(mult, "out", "in1");

        assertThrows(NullPointerException.class, () -> this.connectTo(null, "B", "in2"));
        assertThrows(IllegalArgumentException.class, () -> this.connectTo(mult, "B", "in3"));
        this.connectTo(mult, "B", "in2");

        mult.connectTo(this, "out", "out");

        assertTrue(this.getInputs().contains(aChannel));
        assertTrue(this.getOutputs().contains(outChannel));
    }
}