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
     * The pointer to the value of the most recently added entry to the Map.
     */
    private V latest = null;

    /**
     * Associates the specified value with the specified key in this map.
     * If the map previously contained a mapping for the key, the old
     * value is replaced.
     *
     * Also changes the pointer to the most recently added value, to the value of the new entry put into the map.
     *
     * @param key key with which the specified value is to be associated
     * @param value value to be associated with the specified key
     * @return the previous value associated with {@code key}, or
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
     * @return The value of the most recently added entry, or {@code null} if this map contains no recently added elements.
     */
    @Override
    public V getLatest() {
        return latest;
    }
}
