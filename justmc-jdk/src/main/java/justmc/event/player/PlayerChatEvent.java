package justmc.event.player;

import justmc.event.CancellableEvent;
import justmc.event.PlayerEvent;

public final class PlayerChatEvent implements PlayerEvent, CancellableEvent {
    private PlayerChatEvent() {}

    public native String getMessage();
}
