package justmc.enums;

import justmc.EnumPrimitive;
import justmc.Unsafe;

public final class NoiseRangeMode extends EnumPrimitive {
    public static final NoiseRangeMode ZERO_TO_ONE = Unsafe.cast(EnumPrimitive.of("ZERO_TO_ONE"));
    public static final NoiseRangeMode FULL_RANGE = Unsafe.cast(EnumPrimitive.of("FULL_RANGE"));

    private NoiseRangeMode() {}
}
