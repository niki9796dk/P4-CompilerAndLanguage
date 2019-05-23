package CodeGeneration.DataFlow.Network.Nodes.Blocks;



import AST.Nodes.NodeClasses.NamedNodes.NamedIdNodes.BlockNode;
import CodeGeneration.DataFlow.Executions.MainBlock;
import CodeGeneration.DataFlow.Network.Node;
import CodeGeneration.DataFlow.Network.Nodes.Block;
import CodeGeneration.DataFlow.Network.Nodes.BlocksAndSignalNodes.Operation;
import CodeGeneration.DataFlow.Network.Nodes.BlocksAndSignalNodes.Operations.AbstractOperation;
import CodeGeneration.DataFlow.Network.Nodes.BlocksAndSignalNodes.Operations.BinaryOperations.MatrixOperations.Multiplication;
import CodeGeneration.DataFlow.Network.Nodes.BlocksAndSignalNodes.Operations.BinaryOperations.UnitWiseOperations._Addition;
import CodeGeneration.DataFlow.Network.Nodes.BlocksAndSignalNodes.Operations.BinaryOperations.UnitWiseOperations._Subtraction;
import CodeGeneration.DataFlow.Network.Nodes.BlocksAndSignalNodes.Operations.NullaryOperation.Source;
import CodeGeneration.DataFlow.Network.Nodes.BlocksAndSignalNodes.Operations.UnaryOperations.UnitWiseOperations._Sigmoid;
import CodeGeneration.DataFlow.Network.Nodes.SignalNodes.BounceNodes.FeedforwardBounce;
import CodeGeneration.DataFlow.Network.Nodes.SignalNodes.Channel;
import CodeGeneration.DataFlow.Network.Nodes.SignalNodes.Channels.ListChannel;
import DataStructures.Pair;
import LinearAlgebra.Statics.Matrices;
import LinearAlgebra.Types.Matrices.Matrix;
import MachineLearning.NeuralNetwork.Trainer.Costs.CostFunction;
import MachineLearning.NeuralNetwork.Trainer.Costs.MSECost;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class AbstractBlockTest {
    // Field:
    private AbstractBlock block;
    private Node node1;
    private Node node2;

    @BeforeEach
    void beforeEach() {
        this.block = new testImplementation000();
        this.node1 = new testImplementation000();
        this.node2 = new testImplementation000();
    }

    @Test
    void hasInputChannel() {
        assertTrue(this.block.hasInputChannel("A"));
        assertTrue(this.block.hasInputChannel("B"));
        assertFalse(this.block.hasInputChannel("C"));
        assertFalse(this.block.hasInputChannel("a"));
    }

    @Test
    void hasOutputChannel() {
        assertTrue(this.block.hasOutputChannel("out"));
        assertFalse(this.block.hasOutputChannel("fkc"));
        assertFalse(this.block.hasOutputChannel("Out"));
        assertFalse(this.block.hasOutputChannel("OUT"));
    }

    @Test
    void getChannel() {
        assertNotNull(this.block.getChannel("A"));

        assertThrows(IllegalArgumentException.class, () -> this.block.getChannel("C"));
    }

    @Test
    void getInputChannels() {
        assertTrue(this.block.getInputChannels().containsKey("A"));
        assertTrue(this.block.getInputChannels().containsKey("B"));
    }

    @Test
    void getOutputChannels() {
        assertTrue(block.getOutputChannels().containsKey("out"));
    }

    @Test
    void addNewInputLabel() {
        assertThrows(NullPointerException.class, () -> {
            this.block.addNewInputLabel("id", null);
        });
    }

    @Test
    void connectTo01() {
        assertThrows(RuntimeException.class, () -> {
            Channel channel = new ListChannel();
            Channel out = new ListChannel();
            AbstractBlock localBlock = new testImplementation000();
            localBlock.addNewOutputLabel("id", out);
            localBlock.connectTo(channel);
        });
    }

    @Test
    void connectTo02() {
        assertThrows(RuntimeException.class, () -> {
            Channel channel = new ListChannel();
            AbstractBlock localBlock = new testImplementation000();
            localBlock.getOutputChannels().clear();
            localBlock.connectTo(channel);
        });
    }

    @Test
    void connectTo03() {
        Channel channel = new ListChannel();
        assertEquals(this.block, this.block.connectTo(channel));
    }

    @Test
    void receiveGroupConnection01() {
        assertEquals(this.block, this.block.receiveGroupConnection(this.node1, this.node2));
    }

    @Test
    void receiveGroupConnection02() {
        assertThrows(IllegalArgumentException.class, () -> {
            this.block.receiveGroupConnection(this.node1);
        });
    }

    @Test
    void receiveGroupConnection03() {
        assertThrows(IllegalArgumentException.class, () -> {
            this.block.receiveGroupConnection(this.node1);
        });
    }

    @Test
    void receiveGroupConnection04() {
        assertThrows(IllegalArgumentException.class, () -> {
            Node feedForwardNode1 = new FeedforwardBounce(Matrices.randomMatrix(2, 2));
            Node feedForwardNode2 = new FeedforwardBounce(Matrices.randomMatrix(2, 2));
            this.block.receiveGroupConnection(feedForwardNode1, feedForwardNode2);
        });
    }

    @Test
    void receiveGroupConnection05() {
        Block block1 = new testImplementation000();
        Block block2 = new testImplementation000();
        assertEquals(this.block, this.block.receiveGroupConnection(block1, block2));
    }

    @Test
    void receiveGroupConnection06() {
        assertThrows(IllegalArgumentException.class, () -> {
            Block block1 = new testImplementation000();
            this.block.receiveGroupConnection(block1);
        });
    }

    @Test
    void receiveGroupConnection07() {
        assertThrows(IllegalArgumentException.class, () -> {
            Block block1 = new testImplementation000();
            Block block2 = new testImplementation000();
            Block block3 = new testImplementation000();
            block1.getOutputChannels().put("key", new ListChannel());
            this.block.receiveGroupConnection(block1, block2, block3);
        });
    }

    @Test
    void evaluateInput01() {
        assertThrows(IllegalCallerException.class, () -> {
            Block block1 = new Multiplication();
            block1.getOutputChannels().put("key", new ListChannel());
            block1.evaluateInput(Matrices.randomMatrix(2,2));
        });
    }

    @Test
    void train01() {
        Block block1 = new In_out();
        Matrix inputData = Matrices.constantEntry(10, 10, 1);
        Matrix targetData = Matrices.constantEntry(10, 10, 2);

        CostFunction mse = new MSECost();

        double preError = Matrices.sumMatrix(mse.cost(targetData, block1.evaluateInput(inputData)));

        block1.train(inputData, targetData, 10);

        double postError = Matrices.sumMatrix(mse.cost(targetData, block1.evaluateInput(inputData)));

        assertTrue(postError < preError);
    }
}

class In_out extends AbstractBlock {
    public In_out() {
        // Channels
        this.addNewInputLabel("in", new ListChannel());
        this.addNewOutputLabel("out", new ListChannel());

        // Declarations
        Operation add = new _Addition();
        Source src = new Source(new Pair<>(10, 10));
        Block inOut = new In_out2();

        // Connections
        this.connectTo(add, "in", "in1");
        src.connectTo(add, "out", "in2");
        add.connectTo(inOut, "out", "in");
        inOut.connectTo(this, "out", "out");
    }

    class In_out2 extends AbstractBlock {
        public In_out2() {
            this.addNewInputLabel("in", new ListChannel());
            this.addNewOutputLabel("out", new ListChannel());

            this.connectTo(this, "in", "out");
        }
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