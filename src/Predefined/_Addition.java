package Predefined;

import java.util.ArrayList;
import java.util.List;

public class _Addition implements Operation {
    private List<String> inputs = new ArrayList<>();
    private List<String> outputs = new ArrayList<>();

    public _Addition() {
        inputs.add("A");
        inputs.add("B");
        outputs.add("out");
    }

    public boolean isInput(String string){
        return inputs.contains(string);
    }

    public boolean isOutput(String string){
        return outputs.contains(string);
    }
}
