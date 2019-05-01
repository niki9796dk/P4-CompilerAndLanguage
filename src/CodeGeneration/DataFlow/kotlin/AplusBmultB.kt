package CodeGeneration.DataFlow.kotlin

import CodeGeneration.DataFlow.Network.AbstractBlock
import CodeGeneration.DataFlow.Network.ChannelId
import CodeGeneration.DataFlow.Network.ListChannel
import CodeGeneration.DataFlow.Operations.Addition
import CodeGeneration.DataFlow.Operations.Multiplication
import CodeGeneration.DataFlow.Operations.Operation

/// (X, Y) -> ApBmB -> Z
/// X - ApBmB.A ->

class AplusBmultB : AbstractBlock() {
    init {
        // Channel
        this
                .addInput("A", ListChannel())
                .addInput("B", ListChannel())
                .addOutput("C", ListChannel())

        // Blueprint
        val add = Addition()

        this.connectAll(add)

        val mult = Multiplication()

        add.connectTo(mult, "C", "A")

        this.connectTo(mult, "B", "B")

        mult.connectTo(this, ChannelId.C, ChannelId.C)
    }
}

