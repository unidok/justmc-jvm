package justmc.enums;

import justmc.EnumPrimitive;
import justmc.Unsafe;

public final class LocationCoordinates {
    public static final class Align extends EnumPrimitive {
        public static final Align XYZ = Unsafe.cast(EnumPrimitive.of("ALL"));
        public static final Align XZ = Unsafe.cast(EnumPrimitive.of("X_Z"));
        public static final Align Y = Unsafe.cast(EnumPrimitive.of("Y"));

        private Align() {}
    }

    public static final class Clamp extends EnumPrimitive {
        public static final Clamp XYZ = Unsafe.cast(EnumPrimitive.of("XYZ"));
        public static final Clamp XZ = Unsafe.cast(EnumPrimitive.of("XZ"));
        public static final Clamp Y = Unsafe.cast(EnumPrimitive.of("Y"));

        private Clamp() {}
    }

    public static final class Distance extends EnumPrimitive {
        public static final Distance XYZ = Unsafe.cast(EnumPrimitive.of("THREE_D"));
        public static final Distance XZ = Unsafe.cast(EnumPrimitive.of("TWO_D"));
        public static final Distance Y = Unsafe.cast(EnumPrimitive.of("ALTITUDE"));
        public static final Distance SQUARED_XYZ = Unsafe.cast(EnumPrimitive.of("SQUARED_3D"));
        public static final Distance SQUARED_XZ = Unsafe.cast(EnumPrimitive.of("SQUARED_2D"));

        private Distance() {}
    }

    private LocationCoordinates() {}
}
