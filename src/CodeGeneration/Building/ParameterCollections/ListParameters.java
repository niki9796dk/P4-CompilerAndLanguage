package CodeGeneration.Building.ParameterCollections;

import CodeGeneration.Building.Parameter;
import CodeGeneration.Building.ParameterCollection;

import java.util.ArrayList;
import java.util.List;

public class ListParameters implements ParameterCollection {
    private List<Parameter> parameterList = new ArrayList<>();

    @Override
    public void addParameter(Parameter parameter) {
        this.parameterList.add(parameter);
    }

    @Override
    public List<Parameter> getParameterList() {
        return this.parameterList;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        builder.append("("); // Append params start

        if (this.getParameterList().size() != 0) {
            for (Parameter parameter : this.getParameterList()) {
                builder.append(parameter)
                        .append(", ");
            }

            builder.subSequence(0, builder.length()-2); // Remove last ", " sequence
        }

        builder.append(")"); // Append params end

        return builder.toString();
    }
}
