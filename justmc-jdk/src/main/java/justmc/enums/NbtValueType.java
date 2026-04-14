package justmc.enums;

import justmc.EnumPrimitive;
import justmc.Primitive;
import justmc.Text;
import justmc.Unsafe;

public final class NbtValueType<T> extends EnumPrimitive {
    public static final NbtValueType<Primitive> ANY_VALUE = Unsafe.cast(EnumPrimitive.of("ANY_VALUE"));
    public static final NbtValueType<Text> NBT_STRING = Unsafe.cast(EnumPrimitive.of("NBT_STRING"));

    private NbtValueType() {}
}
