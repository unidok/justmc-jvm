package me.unidok.jjvm.field

import me.unidok.jjvm.JarTranslator

interface FieldReplacer {
    fun replace(owner: String, name: String, context: JarTranslator.Context): Boolean
}