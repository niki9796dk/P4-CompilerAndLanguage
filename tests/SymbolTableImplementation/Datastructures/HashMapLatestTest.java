package SymbolTableImplementation.Datastructures;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HashMapLatestTest {

    @Test
    void test001() {
        MapLatest<String, String> testMap001 = new HashMapLatest<>();

        assertNull(testMap001.getLatest());

        testMap001.put("K0", "V0");

        assertTrue(testMap001.containsKey("K0"));
        assertTrue(testMap001.containsValue("V0"));

        assertEquals("V0", testMap001.getLatest());

        testMap001.put("K1", "V1");

        assertEquals("V1", testMap001.getLatest());

    }
}