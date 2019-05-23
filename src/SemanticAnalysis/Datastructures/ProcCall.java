package SemanticAnalysis.Datastructures;

import AST.Enums.NodeEnum;
import AST.Nodes.AbstractNodes.Nodes.AbstractNode;
import AST.Nodes.AbstractNodes.Nodes.AbstractNodes.NumberedNodes.NamedNode;
import AST.Nodes.AbstractNodes.Nodes.AbstractNodes.NumberedNodes.NamedNodes.NamedIdNode;
import AST.Nodes.NodeClasses.NamedNodes.NamedIdNodes.SelectorNode;
import AST.Nodes.NodeClasses.NamedNodes.ParamsNode;
import AST.Nodes.NodeClasses.NamedNodes.ProcedureCallNode;
import SymbolTableImplementation.BlockScope;
import TypeChecker.TypeSystem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProcCall {
    private ProcedureCallNode proc;
    private Map<String, String> parametersBuild;

    public ProcCall(ProcedureCallNode proc, TypeSystem typeSystem, String currentBlockId, String currentSubScope) {
        this.proc = proc;
        this.parametersBuild = new HashMap<>();

        ParamsNode params = proc.findFirstChildOfClass(ParamsNode.class);

        if (params != null){
            ParamsNode destinationParams = typeSystem.getSymbolTable().getSubScope(currentBlockId, BlockScope.PROCEDURE_PREFIX + proc.findFirstChildOfClass(SelectorNode.class).getId())
                    .getNode().findFirstChildOfClass(ParamsNode.class);

            AbstractNode destinationChild = destinationParams.getChild();
            for (AbstractNode child = params.getChild(); child != null; child = child.getSib(), destinationChild = destinationChild.getSib()){
                NamedIdNode toBuild = (NamedIdNode) typeSystem.followNodeToBuild(child, currentBlockId, currentSubScope);
                String buildToString = toBuild.toString();

                boolean isThis = toBuild.getId().equals("this");

                if (isThis) {
                    buildToString = currentBlockId;

                } else if (toBuild.getNodeEnum().equals(NodeEnum.SELECTOR) && toBuild.getChild() != null){
                    // Channel case
                    SelectorNode select = new SelectorNode(toBuild.getId());
                    select.setNumber(toBuild.getNumber());

                    toBuild = (NamedIdNode) typeSystem.followNodeToBuild(select, currentBlockId, currentSubScope);
                    buildToString = toBuild.toString();
                }

                parametersBuild.put(((NamedIdNode) destinationChild).getId(), buildToString);
            }
        }
    }

    public ProcedureCallNode getProc() {
        return proc;
    }

    public String getBuildOfParameter(NamedIdNode node){
        return getBuildOfParameter(node.getId());
    }

    public String getBuildOfParameter(String parameterId){
        return parametersBuild.get(parameterId);
    }
}
