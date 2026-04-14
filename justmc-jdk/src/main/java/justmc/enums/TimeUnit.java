package justmc.enums;

import justmc.EnumPrimitive;
import justmc.Unsafe;

public final class TimeUnit extends EnumPrimitive {
    public static final TimeUnit TICK = Unsafe.cast(EnumPrimitive.of("TICKS"));
    public static final TimeUnit SECOND = Unsafe.cast(EnumPrimitive.of("SECONDS"));
    public static final TimeUnit MINUTE = Unsafe.cast(EnumPrimitive.of("MINUTES"));

    private TimeUnit() {}
}
