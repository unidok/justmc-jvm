package justmc.event.player;

import justmc.event.*;

public final class PlayerLeftClickEvent implements PlayerEvent, CancellableEvent,
        BlockEvent, BlockFaceEvent, ItemEvent {
    private PlayerLeftClickEvent() {}

    public native boolean hasBlock();
}
