package CodeGeneration.Building;

import java.util.Collection;

public interface ParameterCollection {
    void addParameter(Parameter parameter);

    Collection<Parameter> getParameterList();

    String toCallParameters();
}
