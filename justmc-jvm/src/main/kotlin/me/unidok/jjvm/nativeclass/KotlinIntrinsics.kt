package me.unidok.jjvm.nativeclass

object KotlinIntrinsics {
    fun register() {
//        NativeClasses.registerMethods("kotlin/jvm/internal/Intrinsics", hashMapOf(
//            "checkNotNullParameter(Ljava/lang/Object;Ljava/lang/String;)V" to {
//                val obj = args[0].translate(it, null)
//                val paramName = args[1].translate(it, null)
//                val className = it.clazz.name
//                val methodName = it.method.name
//                it.addOperation(JustOperation(
//                    "if_variable_equals", mapOf(
//                        "value" to obj,
//                        "compare" to NumberValue.CONST_0
//                    ), listOf(
//                        JustOperation(
//                            "control_call_exception", mapOf(
//                            "type" to EnumValue("ERROR"),
//                            "message" to TextValue("Parameter specified as non-null is null: method $className.$methodName, parameter $paramName")
//                        ))
//                    ))
//                )
//                null
//            },
//        ))
    }
}