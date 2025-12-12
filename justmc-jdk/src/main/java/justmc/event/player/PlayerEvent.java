package justmc.event.player;

import justmc.Player;

public interface PlayerEvent {
    default Player getPlayer() {
        return Player.DEFAULT;
    }
}