package justmc.entity;

import justmc.*;
import justmc.enums.NbtValueType;
import justmc.enums.TextMerging;

public interface Player extends LivingEntity {
    default void sendMessage(String message) {
        sendMessage(Text.legacy(message));
    }

    default void sendMessage(Primitive message) {
        operation("player_send_message", MapPrimitive.of(
                Pair.of("messages", message)
        ));
    }

    default void sendMessage(ListPrimitive<?> messages, TextMerging merging) {
        operation("player_send_message", MapPrimitive.of(
                Pair.of("messages", messages),
                Pair.of("merging", merging)
        ));
    }

    default void sendActionBar(Primitive message) {
        operation("player_send_action_bar", MapPrimitive.of(
                Pair.of("messages", message)
        ));
    }

    default void sendActionBar(ListPrimitive<?> messages, TextMerging.ActionBar merging) {
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
}