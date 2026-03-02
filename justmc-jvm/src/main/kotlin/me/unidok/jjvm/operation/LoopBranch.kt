package me.unidok.jjvm.operation

import me.unidok.jjvm.TranslationContext
import me.unidok.jjvm.util.JustOperation

class LoopBranch(
    @JvmField val operations: List<Operation>
) : Operation {
    override fun translate(context: TranslationContext) {
        context.addOperation(JustOperation(
            "repeat_forever",
            emptyMap(),
            context.translateChild(operations)
        ))
    }
}