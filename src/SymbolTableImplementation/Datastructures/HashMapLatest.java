package SymbolTableImplementation.Datastructures;

import java.util.HashMap;

public class HashMapLatest<K,V> extends HashMap<K, V> implements MapLatest<K,V> {

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
