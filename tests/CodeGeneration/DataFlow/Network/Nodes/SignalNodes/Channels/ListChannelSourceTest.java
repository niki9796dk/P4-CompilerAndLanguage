package CodeGeneration.DataFlow.Network.Nodes.SignalNodes.Channels;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ListChannelSourceTest {

    @Test
    void isReady() {
        assertTrue(new ListChannelSource().isReady());
    }

    @Test
    void isSource() {
        assertTrue(new ListChannelSource().isSource());
    }

    @Test
    void acceptReadySignal() {
        new ListChannelSource().isSource();
    }
}