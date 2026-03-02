package justmc.event;

import justmc.GameValue;
import justmc.Item;
import justmc.Util;

public interface ItemEvent {
    default Item getItem() {
        return Util.asItem(GameValue.get("event_item"));
    }
}
