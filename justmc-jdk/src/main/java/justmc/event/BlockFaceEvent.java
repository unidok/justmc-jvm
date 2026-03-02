package justmc.event;

import justmc.GameValue;
import justmc.enums.BlockFace;

public interface BlockFaceEvent {
    default BlockFace getBlockFace() {
        return (BlockFace) GameValue.get("event_block_face");
    }
}
