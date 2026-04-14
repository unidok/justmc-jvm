package justmc.enums;

import justmc.EnumPrimitive;
import justmc.Unsafe;

public final class FluidCollisionMode extends EnumPrimitive {
    public static final FluidCollisionMode NEVER = Unsafe.cast(EnumPrimitive.of("NEVER"));
    public static final FluidCollisionMode SOURCE_ONLY = Unsafe.cast(EnumPrimitive.of("SOURCE_ONLY"));
    public static final FluidCollisionMode ALWAYS = Unsafe.cast(EnumPrimitive.of("ALWAYS"));

    private FluidCollisionMode() {}
}
