package justmc.event.player;

import justmc.Item;
import justmc.Location;
import justmc.event.BlockEvent;
import justmc.event.CancellableEvent;
import justmc.event.ItemEvent;

public final class PlayerRightClickEvent implements PlayerEvent, BlockEvent, ItemEvent, CancellableEvent {
    private PlayerRightClickEvent() {}
}
