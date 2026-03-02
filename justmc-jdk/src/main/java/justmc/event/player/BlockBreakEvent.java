package justmc.event.player;

import justmc.event.BlockEvent;
import justmc.event.CancellableEvent;
import justmc.event.PlayerEvent;

public final class BlockBreakEvent implements PlayerEvent, CancellableEvent, BlockEvent {
    private BlockBreakEvent() {}
}
