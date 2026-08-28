package justmc.enums;

import justmc.EnumPrimitive;
import justmc.Unsafe;

public final class ScoreboardFormatting extends EnumPrimitive {
    public static ScoreboardFormatting BLANK = Unsafe.cast(EnumPrimitive.of("BLANK"));
    public static ScoreboardFormatting FIXED = Unsafe.cast(EnumPrimitive.of("FIXED"));
    public static ScoreboardFormatting STYLED = Unsafe.cast(EnumPrimitive.of("STYLED"));
    public static ScoreboardFormatting RESET = Unsafe.cast(EnumPrimitive.of("RESET"));

    private ScoreboardFormatting() {}
}
