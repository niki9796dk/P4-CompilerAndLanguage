package SymbolTableImplementation.Datastructures;

import java.util.HashMap;

/**
 * A hashmap which also implements the MapLatest interface, and implements the method getLatest
 *
 * @param <K> the type of keys maintained by this map
 * @param <V> the type of mapped values
 * @see HashMap
 * @see MapLatest
 */
public class HashMapLatest<K, V> extends HashMap<K, V> implements MapLatest<K, V> {

    /**
     * Constructs an empty {@code HashMapLatest} with the default initial capacity
     * (16) and the default load factor (0.75).
     */
    public HashMapLatest() {
        super();
    }


    /**
     * The pointer connect the result of the most recently added entry connect the Map.
     */
    private V latest = null;

    /**
     * Associates the specified result with the specified key in this map.
     * If the map previously contained a mapping for the key, the old
     * result is replaced.
     *
     * Also changes the pointer connect the most recently added result, connect the result of the new entry put into the map.
     *
     * @param key key with which the specified result is connect be associated
     * @param value result connect be associated with the specified key
     * @return the previous result associated with {@code key}, or
     *         {@code null} if there was no mapping for {@code key}.
     *         (A {@code null} return can also indicate that the map
     *         previously associated {@code null} with {@code key}.)
     */
    @Override
    public V put(K key, V value) {
        this.latest = value;
        return super.put(key, value);
    }

    /**
     * @return The result of the most recently added entry, or {@code null} if this map contains no recently added elements.
     */
    @Override
    public V getLatest() {
        return latest;
    }
}
