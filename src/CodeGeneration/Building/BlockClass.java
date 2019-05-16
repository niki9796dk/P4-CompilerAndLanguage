package CodeGeneration.Building;

import CodeGeneration.Building.CodeScopes.SimpleCodeScope;

import java.util.ArrayList;
import java.util.List;

public class BlockClass {

    private String className;
    private String classPackage;

    private List<String> imports = new ArrayList<>();
    private CodeScope blueprint = new SimpleCodeScope("blueprint");
    private List<CodeScope> procedures = new ArrayList<>();

    public BlockClass(String className, String classPackage) {
        this.className = className;
        this.classPackage = classPackage;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        // Append package
        builder.append("package ")
                .append(this.classPackage)
                .append(";\n");

        // Append imports
        for (String imp : this.imports) {
            builder.append("import ")
                    .append(imp)
                    .append(";");
        }

        // Start class
        builder.append("public class ")
                .append(this.className)
                .append(" {\n");

        // Start constructor
        builder.append("public ")
                .append(this.className)
                .append(this.blueprint.getParameters()) // Parameters
                .append(" {\n")                         // Start of content
                .append("this.blueprint(")              // Start of call to blueprint
                .append("MyParams")                     // Call params TODO: connect to the real params
                .append(");")                           // End of call to blueprint
                .append("}\n");                         // End of constructor

        // Append the blueprint function
        builder.append(this.blueprint);

        // Append all procedure functions
        for (CodeScope procedure : this.procedures) {
            builder.append(procedure)
                    .append("\n\n");
        }

        // End class
        builder.append("}");

        // Return
        return builder.toString();
    }
}
