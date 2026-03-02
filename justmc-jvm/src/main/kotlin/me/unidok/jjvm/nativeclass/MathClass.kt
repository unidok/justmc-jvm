package me.unidok.jjvm.nativeclass

import me.unidok.jjvm.nativeclass.NativeClasses
import me.unidok.jjvm.operand.DynamicConstant
import me.unidok.justcode.value.StringNumberValue

object MathClass {
    fun register() {
        NativeClasses.registerMethods("justmc/Math", hashMapOf(
            "random()D" to {
                DynamicConstant.valueOf(StringNumberValue("%random%"))
            }
        ))
    }
}