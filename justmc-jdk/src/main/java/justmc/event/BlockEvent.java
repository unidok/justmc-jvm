package justmc.event;

import justmc.Block;
import justmc.GameValue;
import justmc.Location;
import justmc.Util;

public interface BlockEvent {
    default Location getBlockLocation() {
        return Util.asLocation(GameValue.get("event_block_location"));
    }

    default Block getBlock() {
        return Util.asBlock(GameValue.get("event_block"));
    }
}
