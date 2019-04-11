package SymbolTableImplementation.Datastructures;

import java.util.Map;

/**
 * An extension of the Map interface. Ths further specifies the getLatest method.
 * @param <K> the type of keys maintained by this map
 * @param <V> the type of mapped values
 * @see Map
 */
public interface MapLatest<K, V> extends Map<K, V> {
    /**
     * @return The value of the most recently added entry, or {@code null} if this map contains no recently added elements.
     */
    V getLatest();
}
