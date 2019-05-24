package CodeGeneration.DataFlow.Network.Nodes.BlocksAndSignalNodes.Operations.UnaryOperations;

import AST.Nodes.NodeClasses.NamedNodes.NamedIdNodes.DummyNode;
import CodeGeneration.DataFlow.Network.Nodes.SignalNodes.Channel;
import LinearAlgebra.Types.Matrices.Matrix;
import MachineLearning.NeuralNetwork.ANN.ActivactionFunctions.ActivationFunction;
import CompilerExceptions.TypeExceptions.ShouldNotHappenException;

import java.util.HashMap;

public abstract class UnaryFunctionAbstractOperation extends UnaryAbstractOperation {

    protected Matrix operation(Matrix in) {
        return this.getFunction().activation(in);
    }

    protected HashMap<Channel, Matrix> operationBackpropagation(Channel in, Channel out) {
        HashMap<Channel, Matrix> backpropResults = new HashMap<>();

        backpropResults.put(in, this.calculateInDerivatives(in.getResult(), out.getResultBackpropagation()));

        return backpropResults;
    }

    protected Matrix calculateInDerivatives(Matrix in, Matrix out) {
        return out.compMult(this.getAfDeri(in, this.result));
    }

    private Matrix getAfDeri(Matrix net, Matrix out) {
        switch (this.getFunction().getMatrixPref()) {
            case NET:
                return this.getFunction().activationPrime(net);

            case OUT:
                return this.getFunction().activationPrime(out);

            default:
                throw new ShouldNotHappenException(new DummyNode(), "No such matrix pref?");
        }
    }

    protected abstract ActivationFunction getFunction();

}
