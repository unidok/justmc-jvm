package justmc.enums;

import justmc.EnumPrimitive;
import justmc.Unsafe;

public final class SelectionMode extends EnumPrimitive {
    public static final SelectionMode DEFAULT = Unsafe.cast(EnumPrimitive.of("DEFAULT"));
    public static final SelectionMode CURRENT = Unsafe.cast(EnumPrimitive.of("CURRENT"));
    public static final SelectionMode EMPTY = Unsafe.cast(EnumPrimitive.of("EMPTY"));
    public static final SelectionMode FOR_EACH = Unsafe.cast(EnumPrimitive.of("FOR_EACH"));

    private SelectionMode() {}
}
