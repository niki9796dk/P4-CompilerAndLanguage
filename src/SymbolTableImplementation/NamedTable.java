package SymbolTableImplementation;

import Enums.AnsiColor;
import SymbolTableImplementation.Datastructures.HashMapLatest;
import SymbolTableImplementation.Datastructures.MapLatest;

import java.util.Map;

/**
 * An object that maps String keys to values. A NamedTable cannot contain duplicate keys, keys must be Strings, and each key can map to at most one result.
 *
 * @param <T> The type of mapped values
 */
public class NamedTable<T> {

    /**
     * Constructs an empty {@code NamedTable} with the default initial capacity
     */
    public NamedTable() {
    }

    private MapLatest<String, T> table = new HashMapLatest<>();

    /**
     * Retrieve a result to which the specified key is mapped,
     * or {@code null} if this map contains no mapping for the key.
     *
     * @param name The key of the variable.
     * @return Returns the result to which the specified key is mapped, or {@code null} if this map contains no mapping for the key.
     */
    public T getEntry(String name) {
        return table.get(name);
    }

    /**
     * @return The result of the most recently added entry, or {@code null} if this map contains no recently added elements.
     */
    public T getLatest() {
        return this.table.getLatest();
    }

    /**
     * Associates the specified result with the specified key in this Table.
     * If the map previously contained a mapping for the key, the old result is replaced by the specified result.
     * @param name The key of the new result.
     * @param entry The result to map to the key.
     * @return the previous result associated with the key, or null if there was no mapping for the key.
     */
    public T setEntry(String name, T entry) {
        return this.table.put(name, entry);
    }

    /**
     * The to string method.
     * @return a string representation of the object.
     */
    @Override
    public String toString() {
        if (this.table.size() == 0) {
            return AnsiColor.RED + "\t\t\tEmpty" + AnsiColor.RESET;
        }

        StringBuilder builder = new StringBuilder();

        for (Map.Entry<String, T> mapEntry : this.table.entrySet()) {
            String key = mapEntry.getKey();
            T value = mapEntry.getValue();

            builder
                    .append(value.toString())
                    .append("\n");
        }

        return builder.toString();
    }
}
