package justmc;

import justmc.enums.BlockFace;
import justmc.event.BlockFaceEvent;
import org.jetbrains.annotations.Nullable;

public final class RaycastResult {
    private RaycastResult() {}

    public native @Nullable Location getLocation();
    public native @Nullable Location getBlockLocation();
    public native @Nullable BlockFace getFace();
    public native @Nullable String getEntity();
}
