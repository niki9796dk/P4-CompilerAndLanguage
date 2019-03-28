package SymbolTableImplementation;

import Enums.AnsiColor;

import java.util.HashMap;
import java.util.Map;

public class NamedTable<T> {
    private Map<String, T> table = new HashMap<>();
    private String latestName = null;

    public T getEntry(String name) {
        return table.get(name);
    }

    public T getLatest() {
        return this.getEntry(this.latestName);
    }

    public T setEntry(String name, T entry) {
        this.latestName = name;
        return table.put(name, entry);
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
