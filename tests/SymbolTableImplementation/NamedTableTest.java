package SymbolTableImplementation;

import Enums.AnsiColor;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class NamedTableTest {

    @Test
    void test000() {
        NamedTable<String> testMap001 = new NamedTable<>();

        assertNull(testMap001.getLatest());
        assertNull(testMap001.getEntry("K0"));
        assertNull(testMap001.getEntry("V0"));

        testMap001.setEntry("K0", "V0");

        assertEquals("V0", testMap001.getEntry("K0"));

        assertEquals("V0", testMap001.getLatest());

        testMap001.setEntry("K1", "V1");

        assertEquals("V1", testMap001.getLatest());
        assertEquals("V1", testMap001.getEntry("K1"));
    }

    @Test
    void toString000() {
        NamedTable<String> testMap001 = new NamedTable<>();

        assertEquals(AnsiColor.RED + "\t\t\tEmpty" + AnsiColor.RESET, testMap001.toString());

        testMap001.setEntry("K0", "V0");

        assertEquals("V0\n", testMap001.toString());
    }
}