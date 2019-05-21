package CodeGeneration.Building;

import java.util.Collection;
import java.util.List;

public interface ParameterCollection {
    void addParameter(Parameter parameter);
    Collection<Parameter> getParameterList();
    String toCallParameters();
}
