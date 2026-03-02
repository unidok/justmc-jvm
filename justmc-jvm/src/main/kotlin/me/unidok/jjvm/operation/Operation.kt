package me.unidok.jjvm.operation

import me.unidok.jjvm.TranslationContext

interface Operation {
    fun translate(context: TranslationContext)
}