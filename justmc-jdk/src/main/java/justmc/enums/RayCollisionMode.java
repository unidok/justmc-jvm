package justmc.enums;

import justmc.EnumPrimitive;
import justmc.Unsafe;

public final class RayCollisionMode extends EnumPrimitive {
    public static final RayCollisionMode ONLY_BLOCKS = Unsafe.cast(EnumPrimitive.of("ONLY_BLOCKS"));
    public static final RayCollisionMode BLOCKS_AND_ENTITIES = Unsafe.cast(EnumPrimitive.of("BLOCKS_AND_ENTITIES"));
    public static final RayCollisionMode ONLY_ENTITIES = Unsafe.cast(EnumPrimitive.of("ONLY_ENTITIES"));

    private RayCollisionMode() {}
}