package justmc.enums;

import justmc.EnumPrimitive;
import justmc.Unsafe;

public final class TextParsing extends EnumPrimitive {
    public static final TextParsing PLAIN = Unsafe.cast(EnumPrimitive.of("PLAIN"));
    public static final TextParsing LEGACY = Unsafe.cast(EnumPrimitive.of("LEGACY"));
    public static final TextParsing MINIMESSAGE = Unsafe.cast(EnumPrimitive.of("MINIMESSAGE"));
    public static final TextParsing JSON = Unsafe.cast(EnumPrimitive.of("JSON"));

    private TextParsing() {}
}
