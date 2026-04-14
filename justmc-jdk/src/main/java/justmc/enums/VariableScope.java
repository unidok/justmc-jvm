package justmc.enums;

import justmc.EnumPrimitive;
import justmc.Unsafe;

public final class VariableScope extends EnumPrimitive {
    public static final VariableScope GAME = Unsafe.cast(EnumPrimitive.of("GAME"));
    public static final VariableScope SAVE = Unsafe.cast(EnumPrimitive.of("SAVE"));
    public static final VariableScope LOCAL = Unsafe.cast(EnumPrimitive.of("LOCAL"));
    public static final VariableScope LINE = Unsafe.cast(EnumPrimitive.of("LINE"));

    private VariableScope() {}
}
