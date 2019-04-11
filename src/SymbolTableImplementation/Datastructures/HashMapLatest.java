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

    V latest = null;

    @Override
    public V put(K key, V value) {
        this.latest = value;
        return super.put(key, value);
    }

    @Override
    public V getLatest() {
        return latest;
    }
}
