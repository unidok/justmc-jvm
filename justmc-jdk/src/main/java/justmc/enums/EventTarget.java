package justmc.enums;

import justmc.EnumPrimitive;
import justmc.Unsafe;

public final class EventTarget extends EnumPrimitive {
    public static final EventTarget DEFAULT = Unsafe.cast(EnumPrimitive.of("DEFAULT"));
    public static final EventTarget KILLER = Unsafe.cast(EnumPrimitive.of("KILLER"));
    public static final EventTarget DAMAGER = Unsafe.cast(EnumPrimitive.of("DAMAGER"));
    public static final EventTarget VICTIM = Unsafe.cast(EnumPrimitive.of("VICTIM"));
    public static final EventTarget SHOOTER = Unsafe.cast(EnumPrimitive.of("SHOOTER"));
    public static final EventTarget PROJECTILE = Unsafe.cast(EnumPrimitive.of("PROJECTILE"));

    private EventTarget() {}
}