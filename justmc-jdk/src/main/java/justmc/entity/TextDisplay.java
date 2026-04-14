package justmc.entity;

import justmc.*;
import justmc.enums.TextMerging;

public interface TextDisplay extends Display {
    default void setText(ListPrimitive<?> texts, TextMerging merging) {
        operation("entity_set_text_display_text", MapPrimitive.of(
                Pair.of("displayed_text", texts),
                Pair.of("merging_mode", merging)
        ));
    }
}
