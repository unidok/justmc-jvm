package justmc.event.player;

import justmc.enums.InteractionType;
import justmc.event.*;

public final class PlayerInteractEvent implements PlayerEvent, CancellableEvent,
        ItemEvent, BlockEvent, BlockFaceEvent, EquipmentSlotEvent {
    private PlayerInteractEvent() {}

    public native InteractionType getInteraction();
}
