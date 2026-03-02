package me.unidok.jjvm.nativeclass

import me.unidok.jjvm.nativeclass.NativeClasses
import me.unidok.jjvm.operand.DynamicConstant
import me.unidok.justcode.value.TextValue
import org.objectweb.asm.Type

object JavaClasses {
    private val primitives = hashSetOf(
        "java/lang/Boolean",
        "java/lang/Byte",
        "java/lang/Short",
        "java/lang/Character",
        "java/lang/Integer",
        "java/lang/Long",
        "java/lang/Float",
        "java/lang/Double",
        "java/lang/String",
        "justmc/Block",
        "justmc/CopyableList",
        "justmc/CopyableMap",
        "justmc/Item",
        "justmc/Location",
        "justmc/Potion",
        "justmc/Text",
        "justmc/Variable",
        "justmc/Vector",
    )

    fun register() {
        NativeClasses.registerMethods("java/lang/Object", hashMapOf(
            "<init>()V" to {
                null
            },
            "toString()Ljava/lang/String;" to {
//                val self = self!!
//                val finalType = it.getFinalType(self)
//                if (finalType != null && finalType in primitives) {
//                    return@to DynamicConstant(TextValue(self.toString()))
//                }
                null
            },
        ))
        NativeClasses.registerMethods("java/lang/Boolean", hashMapOf(
            "valueOf(Z)Ljava/lang/Boolean;" to { arg(0) }
        ))
        NativeClasses.registerMethods("java/lang/Byte", hashMapOf(
            "valueOf(B)Ljava/lang/Byte;" to { arg(0) }
        ))
        NativeClasses.registerMethods("java/lang/Character", hashMapOf(
            "valueOf(C)Ljava/lang/Character;" to { arg(0) }
        ))
        NativeClasses.registerMethods("java/lang/Short", hashMapOf(
            "valueOf(S)Ljava/lang/Short;" to { arg(0) }
        ))
        NativeClasses.registerMethods("java/lang/Integer", hashMapOf(
            "valueOf(I)Ljava/lang/Integer;" to { arg(0) }
        ))
        NativeClasses.registerMethods("java/lang/Long", hashMapOf(
            "valueOf(J)Ljava/lang/Long;" to { arg(0) }
        ))
        NativeClasses.registerMethods("java/lang/Float", hashMapOf(
            "valueOf(F)Ljava/lang/Float;" to { arg(0) }
        ))
        NativeClasses.registerMethods("java/lang/Double", hashMapOf(
            "valueOf(D)Ljava/lang/Double;" to { arg(0) }
        ))
    }
}