package justmc.event;

import justmc.Block;
import justmc.Location;
import justmc.Util;

public interface BlockEvent {
    default Location getBlockLocation() {
        return Util.asLocation(Util.getGameValue("event_block_location"));
    }

    default Block getBlock() {
        return Util.asBlock(Util.getGameValue("event_block"));
    }
}
