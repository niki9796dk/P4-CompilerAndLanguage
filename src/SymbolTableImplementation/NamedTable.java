package SymbolTableImplementation;

import Enums.AnsiColor;
import SymbolTableImplementation.Datastructures.HashMapLatest;
import SymbolTableImplementation.Datastructures.MapLatest;

import java.util.Map;

/**
 * An object that maps String keys to values. A NamedTable cannot contain duplicate keys, keys must be Strings, and each key can map to at most one value.
 *
 * @param <T> The type of mapped values
 */
public class NamedTable<T> {
    private MapLatest<String, T> table = new HashMapLatest<>();

    /**
     * Retrieve a value to which the specified key is mapped,
     * or {@code null} if this map contains no mapping for the key.
     *
     * @param name The key of the variable.
     * @return Returns the value to which the specified key is mapped, or {@code null} if this map contains no mapping for the key.
     */
    public T getEntry(String name) {
        return table.get(name);
    }

    /**
     * @return The value of the most recently added entry, or {@code null} if this map contains no recently added elements.
     */
    public T getLatest() {
        return this.table.getLatest();
    }

    /**
     * Associates the specified value with the specified key in this Table.
     * If the map previously contained a mapping for the key, the old value is replaced by the specified value.
     * @param name The key of the new value.
     * @param entry The value to map to the key.
     * @return the previous value associated with the key, or null if there was no mapping for the key.
     */
    public T setEntry(String name, T entry) {
        return this.table.put(name, entry);
    }

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
