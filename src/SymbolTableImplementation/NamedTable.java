package SymbolTableImplementation;

import Enums.AnsiColor;
import SymbolTableImplementation.Datastructures.HashMapLatest;
import SymbolTableImplementation.Datastructures.MapLatest;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class NamedTable<T>  {
    private MapLatest<String, T> table = new HashMapLatest<>();
    //private String latestName = null;

    public T getEntry(String name) {
        return table.get(name);
    }

    public T getLatest() {
        return this.table.getLatest();
    }

    public T setEntry(String name, T entry) {
        return this.table.put(name, entry);
    }

    /*
    public T getLatest() {
        return this.getEntry(this.latestName);
    }

    public T setEntry(String name, T entry) {
        this.latestName = name;
        return table.put(name, entry);
    }
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
