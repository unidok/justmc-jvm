package me.unidok.jjvm.nativeclass

import me.unidok.jjvm.nativeclass.NativeClasses
import me.unidok.jjvm.operand.DynamicConstant
import me.unidok.justcode.value.GameValue

object WorldClass {
    fun register() {
        NativeClasses.registerMethods("justmc/World", hashMapOf(
            "currentTimeMillis()J" to {
                DynamicConstant(GameValue.TIMESTAMP)
            },
            "getCPU()I" to {
                DynamicConstant(GameValue.CPU_USAGE)
            },
        ))
    }
}