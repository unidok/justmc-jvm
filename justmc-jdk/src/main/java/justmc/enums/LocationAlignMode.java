package justmc.enums;

import justmc.EnumPrimitive;
import justmc.Unsafe;

public final class LocationAlignMode extends EnumPrimitive {
    public static final LocationAlignMode BLOCK_CENTER = Unsafe.cast(EnumPrimitive.of("BLOCK_CENTER"));
    public static final LocationAlignMode CORNER = Unsafe.cast(EnumPrimitive.of("CORNER"));

    private LocationAlignMode() {}
}
