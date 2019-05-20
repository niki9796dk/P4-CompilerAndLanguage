package CodeGeneration.Building.Statements.Connections;

import CodeGeneration.Building.Statement;

public class SingleChain implements Statement {
    private String elemIn;
    private String channelIn;

    private String elemOut;
    private String channelOut;

    public SingleChain(String elemIn, String channelIn, String elemOut, String channelOut) {
        this.elemIn = elemIn;
        this.channelIn = channelIn;
        this.elemOut = elemOut;
        this.channelOut = channelOut;
    }

    public SingleChain(Statement elemIn, Statement channelIn, Statement elemOut, Statement channelOut) {
        this(elemIn.toString(), channelIn.toString(), elemOut.toString(), channelOut.toString());
    }

    @Override
    public String toString() {
        return this.elemIn + ".connectTo("+ this.elemOut +", \""+ this.channelIn +"\", \""+ this.channelOut +"\")";
    }
}
