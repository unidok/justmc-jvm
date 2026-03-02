package justmc;

import justmc.annotation.PrimitiveType;

@PrimitiveType
public final class Item {
    public static native Item fromId(String id);
    public static native Item fromData(String id);
}
