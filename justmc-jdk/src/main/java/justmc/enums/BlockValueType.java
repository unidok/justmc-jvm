package justmc.enums;

import justmc.*;

public final class BlockValueType<T> extends EnumPrimitive {
    public static final BlockValueType<Text> ID = Unsafe.cast(EnumPrimitive.of("ID"));
    public static final BlockValueType<Block> ID_WITH_DATA = Unsafe.cast(EnumPrimitive.of("ID_WITH_DATA"));
    public static final BlockValueType<Item> ITEM = Unsafe.cast(EnumPrimitive.of("ITEM"));
    public static final BlockValueType<Text> NAME = Unsafe.cast(EnumPrimitive.of("NAME"));

    private BlockValueType() {}
}
