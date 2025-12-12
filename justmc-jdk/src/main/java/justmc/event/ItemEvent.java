package justmc.event;

import justmc.Block;
import justmc.Item;
import justmc.Util;

public interface ItemEvent {
    default Item getItem() {
        return Util.asItem(Util.getGameValue("event_item"));
    }
}
