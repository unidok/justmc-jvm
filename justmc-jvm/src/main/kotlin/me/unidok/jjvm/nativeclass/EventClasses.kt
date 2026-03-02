package me.unidok.jjvm.nativeclass

import me.unidok.jjvm.operand.DynamicConstant
import me.unidok.jjvm.util.MethodsMap
import me.unidok.jjvm.util.JustOperation
import me.unidok.justcode.value.GameValue
import me.unidok.justcode.value.TextValue

object EventClasses {
    val playerEvents = hashMapOf(
        "PlayerJoinEvent" to "player_join",
        "PlayerQuitEvent" to "player_quit",
        "PlayerChatEvent" to "player_chat",
        "PlayerInteractEvent" to "player_interact",
        "PlayerLeftClickEvent" to "player_left_click",
        "PlayerRightClickEvent" to "player_right_click",
        "BlockBreakEvent" to "player_break_block",
        "BlockPlaceEvent" to "player_place_block",
        "PlayerSwapHandItemsEvent" to "player_swap_hands"
    )

    val entityEvents = hashMapOf(
        "EntityCreateEvent" to "entity_create"
    )

    val worldEvents = hashMapOf(
        "WorldStartEvent" to "world_start",
        "WorldStopEvent" to "world_stop",
        "WebResponseEvent" to "world_web_response",
        "WebExceptionEvent" to "world_web_exception"
    )

    fun register() {
        val methods: MethodsMap = hashMapOf(
            "getPlayer()Ljustmc/Player;" to {
                PlayerClass.DEFAULT
            },
            "cancel()V" to {
                addOperation(JustOperation("game_cancel_event"))
                null
            },
            "uncancel()V" to {
                addOperation(JustOperation("game_uncancel_event"))
                null
            },
            "getBlockLocation()Ljustmc/Location;" to {
                DynamicConstant(GameValue.EVENT_BLOCK_LOCATION)
            },
            "getBlock()Ljustmc/Block;" to {
                DynamicConstant(GameValue.EVENT_BLOCK)
            },
            "getItem()Ljustmc/Item;" to {
                DynamicConstant(GameValue.EVENT_ITEM)
            },
            "getEquipmentSlot()Ljustmc/enums/EquipmentSlot;" to {
                DynamicConstant(GameValue.EVENT_EQUIPMENT_SLOT)
            },
            "getBlockFace()Ljustmc/enums/BlockFace;" to {
                DynamicConstant(GameValue.EVENT_BLOCK_FACE)
            },
            "getInteraction()Ljustmc/enums/InteractionType;" to {
                DynamicConstant(GameValue.EVENT_INTERACTION)
            },
            "getMessage()Ljava/lang/String;" to {
                if (method.owner == "justmc/event/player/PlayerChatEvent") {
                    DynamicConstant(GameValue.EVENT_CHAT_MESSAGE)
                } else {
                    DynamicConstant(GameValue.EVENT_MESSAGE)
                }
            },
            "getUrl()Ljava/lang/String;" to {
                DynamicConstant(GameValue.URL)
            },
            "getResponse()Ljava/lang/String;" to {
                DynamicConstant(GameValue.URL_RESPONSE)
            },
            "getResponseCode()I" to {
                DynamicConstant(GameValue.URL_RESPONSE_CODE)
            },
            "getHeaders()Ljustmc/CopyableMap;" to {
                DynamicConstant(GameValue.RESPONSE_HEADERS)
            },
            "hasBlock()Z" to {
                when (method.owner) {
                    "justmc/event/player/PlayerLeftClickEvent" -> {
                        GameValue.EVENT_INTERACTION
                        // equals
                        TextValue("left_click_block")
                    }
                    "justmc/event/player/PlayerRightClickEvent" -> {
                        GameValue.EVENT_INTERACTION
                        // equals
                        TextValue("right_click_block")
                    }
                    else -> return@to null
                }
                null
            },
        )
        for (className in playerEvents.keys) {
            NativeClasses.registerMethods("justmc/event/player/$className", methods)
        }
        for (className in entityEvents.keys) {
            NativeClasses.registerMethods("justmc/event/entity/$className", methods)
        }
        for (className in worldEvents.keys) {
            NativeClasses.registerMethods("justmc/event/world/$className", methods)
        }
    }
}