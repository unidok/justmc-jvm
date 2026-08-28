package justmc.entity;

import justmc.*;
import justmc.enums.NbtValueType;
import justmc.enums.TextMerging;

public interface Player extends LivingEntity {
    default void sendMessage(Text message) {
        operation("player_send_message", MapPrimitive.of(
                Pair.of("messages", message)
        ));
    }

    default void sendMessage(ListPrimitive<Text> messages, TextMerging merging) {
        operation("player_send_message", MapPrimitive.of(
                Pair.of("messages", messages),
                Pair.of("merging", merging)
        ));
    }

    default void sendActionBar(Text message) {
        operation("player_send_action_bar", MapPrimitive.of(
                Pair.of("messages", message)
        ));
    }

    default void sendActionBar(ListPrimitive<Text> messages, TextMerging.ActionBar merging) {
        operation("player_send_action_bar", MapPrimitive.of(
                Pair.of("messages", messages),
                Pair.of("merging", merging)
        ));
    }

    default boolean isSneaking() {
        return conditional("if_player_is_sneaking", MapPrimitive.empty()).get();
    }

    @Override
    default <T> T getNbt(ListPrimitive<Text> path, NbtValueType<T> valueType) {
        var result = Variable.result();
        operation("player_get_nbt", MapPrimitive.of(
                Pair.of("variable", result),
                Pair.of("path", path),
                Pair.of("value_type", valueType)
        ));
        return Unsafe.cast(result);
    }

    default void setEntityIsHidden(ListPrimitive<EntityId> entityIds, boolean hide) {
        operation("player_hide_entity", MapPrimitive.of(
                Pair.of("name_or_uuid", entityIds),
                Pair.of("hide", EnumPrimitive.of(hide))
        ));
    }

    default void showEntity(ListPrimitive<EntityId> entityIds) {
        setEntityIsHidden(entityIds, false);
    }

    default void hideEntity(ListPrimitive<EntityId> entityIds) {
        setEntityIsHidden(entityIds, true);
    }

    default void showScoreboard(Scoreboard scoreboard) {
        operation("player_show_scoreboard", MapPrimitive.of(
                Pair.of("id", scoreboard.id)
        ));
    }

    default void hideScoreboard() {
        operation("player_hide_scoreboard", MapPrimitive.empty());
    }
}