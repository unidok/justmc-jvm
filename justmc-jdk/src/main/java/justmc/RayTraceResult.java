package justmc;

import justmc.enums.BlockFace;
import org.jetbrains.annotations.Nullable;

public final class RayTraceResult extends Primitive {
    public final @Nullable Location location;
    public final @Nullable Location blockLocation;
    public final @Nullable BlockFace blockFace;
    public final @Nullable EntityId entity;

    public RayTraceResult(
            @Nullable Location location,
            @Nullable Location blockLocation,
            @Nullable BlockFace blockFace,
            @Nullable EntityId entity
    ) {
        this.location = location;
        this.blockLocation = blockLocation;
        this.blockFace = blockFace;
        this.entity = entity;
    }
}
