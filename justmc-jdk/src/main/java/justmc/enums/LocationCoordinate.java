package justmc.enums;

import justmc.EnumPrimitive;
import justmc.Unsafe;

public final class LocationCoordinate extends EnumPrimitive {
    public static final LocationCoordinate X = Unsafe.cast(EnumPrimitive.of("X"));
    public static final LocationCoordinate Y = Unsafe.cast(EnumPrimitive.of("Y"));
    public static final LocationCoordinate Z = Unsafe.cast(EnumPrimitive.of("Z"));
    public static final LocationCoordinate YAW = Unsafe.cast(EnumPrimitive.of("YAW"));
    public static final LocationCoordinate PITCH = Unsafe.cast(EnumPrimitive.of("PITCH"));

    private LocationCoordinate() {}
}
