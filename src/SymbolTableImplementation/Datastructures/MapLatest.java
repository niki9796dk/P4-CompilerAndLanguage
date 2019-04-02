package SymbolTableImplementation.Datastructures;

import java.util.Map;

public interface MapLatest<K, V> extends Map<K, V> {
    V getLatest();
}
