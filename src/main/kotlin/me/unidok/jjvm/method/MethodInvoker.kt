package me.unidok.jjvm.method

import me.unidok.jjvm.JarTranslator

interface MethodInvoker {
    fun execute(owner: String, name: String, desc: String, context: JarTranslator.Context): Boolean
}