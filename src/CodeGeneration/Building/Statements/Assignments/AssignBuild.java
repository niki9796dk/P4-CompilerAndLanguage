package CodeGeneration.Building.Statements.Assignments;

import AST.Enums.NodeEnum;
import CodeGeneration.Building.Statement;
import CodeGeneration.Building.Statements.Instantiations.InitBuild;
import CodeGeneration.Building.Statements.SubStatement;
import CodeGeneration.Building.Statements.VariableDeclarations.Enums.JavaTypes;
import SymbolTableImplementation.Scope;
import SymbolTableImplementation.SymbolTable;

public class AssignBuild implements Statement {
    private String leftVar;
    private Statement initBuild;
    private JavaTypes type;

    private AssignBuild(Scope subScope, String leftVar) {
        this.setType(subScope.getVariable(leftVar).getSuperType());
        this.leftVar = leftVar;
    }

    public AssignBuild(Scope subScope, String leftVar, Statement initBuild) {
        this(subScope, leftVar);
        this.initBuild = initBuild;
    }

    public AssignBuild(Scope subScope, String leftVar, String initBuild) {
        this(subScope, leftVar, new InitBuild(initBuild));
    }

    public AssignBuild(Scope subScope, String leftVar, String initBuild, Statement ... paramStatements) {
        this(subScope, leftVar, new InitBuild(initBuild, paramStatements));
    }

    public void setType(JavaTypes type) {
        this.type = type;
    }

    public void setType(NodeEnum nodeEnum) {
        switch (nodeEnum) {
            case BLOCK_TYPE:
                this.setType(JavaTypes.BLOCK);
                break;
            case OPERATION_TYPE:
                this.setType(JavaTypes.OPERATION);
                break;
            case SOURCE_TYPE:
                this.setType(JavaTypes.SOURCE);
                break;
        }
    }

    @Override
    public String toString() {
        return leftVar + " = " + initBuild;
    }
}
