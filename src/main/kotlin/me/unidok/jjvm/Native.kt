package me.unidok.jjvm

import me.unidok.justcode.operation.Operation
import me.unidok.justcode.trigger.Trigger
import me.unidok.justcode.value.*

object Native {
    private val commonEventMethods: Map<String, Map<String, JarTranslator.Context.() -> Unit>> = hashMapOf(
        "getPlayer" to hashMapOf(
            "()Ljustmc/Player;" to {
                stack.addFirst(fields["justmc/Player"]!!["DEFAULT"]!!)
            }
        ),
        "cancel" to hashMapOf(
            "()V" to {
                operations.add(Operation("game_cancel_event"))
            }
        ),
        "uncancel" to hashMapOf(
            "()V" to {
                operations.add(Operation("game_uncancel_event"))
            }
        )
    )

    val methods: Map<String, Map<String, Map<String, JarTranslator.Context.() -> Unit>>> = hashMapOf(
        "java/lang/Object" to hashMapOf(
            "<init>" to hashMapOf(
                "()V" to {
                    stack.removeFirst()
                }
            )
        ),
        "kotlin/jvm/internal/Intrinsics" to hashMapOf(
            "checkNotNullParameter" to hashMapOf(
                "(Ljava/lang/Object;Ljava/lang/String;)V" to {
                    val paramName = stack.removeFirst()
                    val obj = stack.removeFirst()
                    val className = clazz.name
                    val methodName = method.name
                    operations.add(Operation("if_variable_equals", mapOf(
                        "value" to obj,
                        "compare" to NumberValue.CONST_0
                    ), listOf(
                        Operation("control_call_exception", mapOf(
                            "type" to EnumValue("ERROR"),
                            "message" to TextValue("Parameter specified as non-null is null: method $className.$methodName, parameter $paramName")
                        ))
                    )))
                }
            )
        ),
        "justmc/Location" to hashMapOf(
            "of" to hashMapOf(
                "(DDD)Ljustmc/Location;" to {
                    val z = stack.removeFirst()
                    val y = stack.removeFirst()
                    val x = stack.removeFirst()
                    if (x is NumberValue && y is NumberValue && z is NumberValue) {
                        stack.addFirst(LocationValue(x.number, y.number, z.number, 0.0, 0.0))
                    } else {
                        val variable = tempVar()
                        operations.add(Operation("set_variable_set_all_coordinates", mapOf(
                            "variable" to variable,
                            "x" to x,
                            "y" to y,
                            "z" to z
                        )))
                        stack.addFirst(variable)
                    }
                },
                "(DDDFF)Ljustmc/Location;" to {
                    val pitch = stack.removeFirst()
                    val yaw = stack.removeFirst()
                    val z = stack.removeFirst()
                    val y = stack.removeFirst()
                    val x = stack.removeFirst()
                    if (x is NumberValue && y is NumberValue && z is NumberValue && yaw is NumberValue && pitch is NumberValue) {
                        stack.addFirst(LocationValue(x.number, y.number, z.number, yaw.number, pitch.number))
                    } else {
                        val variable = tempVar()
                        operations.add(Operation("set_variable_set_all_coordinates", mapOf(
                            "variable" to variable,
                            "x" to x,
                            "y" to y,
                            "z" to z,
                            "yaw" to yaw,
                            "pitch" to pitch
                        )))
                        stack.addFirst(variable)
                    }
                }
            ),
            "getX" to hashMapOf(
                "()D" to {
                    val location = stack.removeFirst()
                    if (location is LocationValue) {
                        stack.addFirst(NumberValue(location.x))
                    } else {
                        val variable = tempVar()
                        operations.add(Operation("set_variable_get_coordinate", mapOf(
                            "variable" to variable,
                            "location" to location,
                            "type" to EnumValue("X")
                        )))
                        stack.addFirst(variable)
                    }
                }
            ),
            "getY" to hashMapOf(
                "()D" to {
                    val location = stack.removeFirst()
                    if (location is LocationValue) {
                        stack.addFirst(NumberValue(location.y))
                    } else {
                        val variable = tempVar()
                        operations.add(Operation("set_variable_get_coordinate", mapOf(
                            "variable" to variable,
                            "location" to location,
                            "type" to EnumValue("Y")
                        )))
                        stack.addFirst(variable)
                    }
                }
            ),
            "getZ" to hashMapOf(
                "()D" to {
                    val location = stack.removeFirst()
                    if (location is LocationValue) {
                        stack.addFirst(NumberValue(location.z))
                    } else {
                        val variable = tempVar()
                        operations.add(Operation("set_variable_get_coordinate", mapOf(
                            "variable" to variable,
                            "location" to location,
                            "type" to EnumValue("Z")
                        )))
                        stack.addFirst(variable)
                    }
                }
            ),
            "getYaw" to hashMapOf(
                "()F" to {
                    val location = stack.removeFirst()
                    if (location is LocationValue) {
                        stack.addFirst(NumberValue(location.yaw))
                    } else {
                        val variable = tempVar()
                        operations.add(Operation("set_variable_get_coordinate", mapOf(
                            "variable" to variable,
                            "location" to location,
                            "type" to EnumValue("YAW")
                        )))
                        stack.addFirst(variable)
                    }
                }
            ),
            "getPitch" to hashMapOf(
                "()F" to {
                    val location = stack.removeFirst()
                    if (location is LocationValue) {
                        stack.addFirst(NumberValue(location.pitch))
                    } else {
                        val variable = tempVar()
                        operations.add(Operation("set_variable_get_coordinate", mapOf(
                            "variable" to variable,
                            "location" to location,
                            "type" to EnumValue("PITCH")
                        )))
                        stack.addFirst(variable)
                    }
                }
            ),
        ),
        "justmc/Variable" to hashMapOf(
            "save" to hashMapOf(
                "(Ljava/lang/String;)Ljustmc/Variable;" to {
                    stack.addFirst(Variable(stack.removeFirst().toString(), Variable.Scope.SAVE))
                }
            ),
            "game" to hashMapOf(
                "(Ljava/lang/String;)Ljustmc/Variable;" to {
                    stack.addFirst(Variable(stack.removeFirst().toString(), Variable.Scope.GAME))
                }
            ),
            "local" to hashMapOf(
                "(Ljava/lang/String;)Ljustmc/Variable;" to {
                    stack.addFirst(Variable(stack.removeFirst().toString(), Variable.Scope.LOCAL))
                }
            ),
            "line" to hashMapOf(
                "(Ljava/lang/String;)Ljustmc/Variable;" to {
                    stack.addFirst(Variable(stack.removeFirst().toString(), Variable.Scope.LINE))
                }
            ),
            "temp" to hashMapOf(
                "()Ljustmc/Variable;" to {
                    stack.addFirst(tempVar())
                }
            )
        ),
        "justmc/Player" to hashMapOf(
            "sendMessage" to hashMapOf(
                "(Ljava/lang/String;)V" to {
                    val message = stack.removeFirst()
                    val selector = stack.removeFirst()
                    operations.add(Operation("player_send_message", mapOf(
                        "messages" to message
                    ), selection = selector.toString()))
                }
            )
        ),
        "justmc/World" to hashMapOf(
            "timestamp" to hashMapOf(
                "()J" to {
                    stack.addFirst(GameValue.TIMESTAMP)
                }
            ),
            "cpu" to hashMapOf(
                "()I" to {
                    stack.addFirst(GameValue.CPU_USAGE)
                }
            ),
        ),
        "justmc/Util" to hashMapOf(
            "asString" to hashMapOf(
                "(Ljava/lang/Object;)Ljava/lang/String;" to {}
            ),
        ),
        "justmc/event/player/PlayerJoinEvent" to commonEventMethods,
        "justmc/event/player/PlayerLeftClickEvent" to commonEventMethods,
    )

    val eventMapping = hashMapOf(
        "PlayerJoinEvent" to "player_join",
        "PlayerLeftClickEvent" to "player_left_click"
    )

    val fields: Map<String, Map<String, Value>> = hashMapOf(
        "justmc/Player" to hashMapOf(
            "CURRENT" to TextValue("current"),
            "DEFAULT" to TextValue("default_player"),
            "KILLER" to TextValue("killer_player"),
            "DAMAGER" to TextValue("damager_player"),
            "SHOOTER" to TextValue("shooter_player"),
            "VICTIM" to TextValue("victim_player"),
            "RANDOM" to TextValue("random_player"),
            "ALL" to TextValue("all_players")
        )
    )

    val instructions: List<Trigger> = listOf(

    )
}