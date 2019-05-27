package CodeGeneration.DataFlow.Network.Nodes.SignalNodes.Channels;

import CodeGeneration.DataFlow.Network.Nodes.BlocksAndSignalNodes.Operations.NullaryOperation.Source;
import CodeGeneration.DataFlow.Network.Nodes.SignalNodes.Channel;
import LinearAlgebra.Types.Matrices.Matrix;
import LinearAlgebra.Types.Matrices.MatrixBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class AbstractChannelTest {
    ArrayList<AbstractChannel> channels;

    @BeforeEach
    void beforeEach() {
        channels = new ArrayList<>();

        channels.add(
                new ListChannel()
        );
        channels.add(
                new ListChannelSource()
        );
    }

    @Test
    void addTarget_SetSource_Tether() {
        Channel lChannel000 = new ListChannel();
        Channel lChannel001 = new ListChannel();

        Matrix m = MatrixBuilder.buildConstant(2, 2, 5);
        Source in = new Source(m);

        in.getOutputChannel().tether(lChannel000);
        lChannel000.tether(lChannel001);
        in.acceptReadySignal();
        assertEquals(lChannel001.getResult(), m);
    }

    @Test
    void getResultBackpropagation() {
    }

    @Test
    void isReady() {
        Channel lChannel000 = new ListChannel();

        assertFalse(lChannel000.isReady());

        Matrix m = MatrixBuilder.buildConstant(2, 2, 5);
        Source in = new Source(m);

        in.getOutputChannel().tether(lChannel000);

        assertTrue(lChannel000.isReady());
    }

    @Test
    void isSource() {
        Channel lChannel000 = new ListChannel();
        Channel lChannel001 = new ListChannel();

        assertFalse(lChannel000.isSource());
        assertFalse(lChannel001.isSource());

        Matrix m = MatrixBuilder.buildConstant(2, 2, 5);
        Source in = new Source(m);

        in.getOutputChannel().tether(lChannel000);

        assertTrue(lChannel000.isSource());
        assertFalse(lChannel001.isSource());

        lChannel000.tether(lChannel001);

        assertTrue(lChannel000.isSource());
        assertTrue(lChannel001.isSource());

    }

    @Test
    void getSource() {
        Channel lChannel000 = new ListChannel();
        Channel lChannel001 = new ListChannel();

        assertNull(lChannel000.getSource());
        assertNull(lChannel001.getSource());

        lChannel000.tether(lChannel001);

        assertEquals(lChannel000, lChannel001.getSource());
        assertNull(lChannel000.getSource());
    }

    @Test
    void getTargets() {
        Channel lChannel000 = new ListChannel();
        Channel lChannel001 = new ListChannel();
        Channel lChannel002 = new ListChannel();

        lChannel000.addTarget(lChannel001);
        lChannel000.addTarget(lChannel002);

        assertEquals(lChannel000.getTargets().size(), 2);
        assertTrue(lChannel000.getTargets().contains(lChannel001));
        assertTrue(lChannel000.getTargets().contains(lChannel002));
    }

    @Test
    void setSource() {
        Channel lChannel000 = new ListChannel();
        Channel lChannel001 = new ListChannel();
        Channel lChannel002 = new ListChannel();

        assertThrows(NullPointerException.class, () -> lChannel000.setSource(null));


        lChannel002.setSource(lChannel001);
        lChannel001.setSource(lChannel000);

        assertEquals(lChannel000, lChannel001.getSource());
        assertEquals(lChannel001, lChannel002.getSource());

    }
}