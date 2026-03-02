package justmc.event.player;

import justmc.event.*;

public final class PlayerRightClickEvent implements PlayerEvent, CancellableEvent,
        BlockEvent, BlockFaceEvent, ItemEvent, EquipmentSlotEvent {
    private PlayerRightClickEvent() {}

    public native boolean hasBlock();
}
