package me.unidok.jjvm.nativeclass

import me.unidok.jjvm.nativeclass.NativeClasses
import me.unidok.jjvm.operand.DynamicConstant
import me.unidok.jjvm.util.NativeMethod
import me.unidok.justcode.value.ArrayValue

object CopyableListClass {
    fun register() {
        NativeClasses.registerMethods("justmc/CopyableList", hashMapOf(
            "of([Ljava/lang/Object;)Ljustmc/CopyableList;" to {
                //DynamicConstant(Translator.instance(args[0].translate(it, null)))
                DynamicConstant(ArrayValue())
            },
//            "add(Ljava/lang/Object;)Ljustmc/CopyableList;" to {
//                Operand { context, variable ->
//                    val list = Translator.setVariable(context, variable, self!!)
//                    val value = args[0].translate(context, null)
//                    if (list is Variable) context.addOperation(JustOperation(
//                        "set_variable_append_value", mapOf(
//                            "variable" to list,
//                            "values" to value
//                        )
//                    )) else {
//                        list as ArrayValue
//                        list.values.add(value)
//                    }
//                    list
//                }
//            }
        ))
    }
}