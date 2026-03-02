package me.unidok.jjvm.operation

import me.unidok.jjvm.TranslationContext

class TranslatableFutureOperation(
    val block: TranslationContext.() -> Unit
) : Operation {
    override fun translate(context: TranslationContext) {
        block(context)
    }
}