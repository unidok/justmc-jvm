package me.unidok.jjvm.method

import me.unidok.jjvm.JarTranslator

class LocationMethodInvoker : MethodInvoker {
    //fun register()

    override fun execute(
        owner: String,
        name: String,
        desc: String,
        context: JarTranslator.Context
    ): Boolean {
        if (owner != "justmc/Location") return false
        when (name) {
            "of" -> {

            }
        }
        TODO("Not yet implemented")
    }
}