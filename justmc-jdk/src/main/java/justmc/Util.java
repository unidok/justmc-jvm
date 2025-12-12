package justmc;

import justmc.enums.GameValueSelector;

public final class Util {
    private Util() {}

    public static native void operation(String id);
    public static native void operation(String id, CopyableMap args);
    public static native void operation(String id, CopyableMap args, String selector);
    public static native Object getGameValue(String id);
    public static native Object getGameValue(String id, GameValueSelector selector);

    public static native byte asByte(Object o);
    public static native short asShort(Object o);
    public static native int asInt(Object o);
    public static native long asLong(Object o);
    public static native float asFloat(Object o);
    public static native double asDouble(Object o);
    public static native String asString(Object o);
    public static native Text asText(Object o);
    public static native Vector asVector(Object o);
    public static native Location asLocation(Object o);
    public static native Block asBlock(Object o);
    public static native Item asItem(Object o);
    public static native CopyableList asCopyableList(Object o);
    public static native MutableCopyableList asMutableCopyableList(Object o);
    public static native CopyableMap asCopyableMap(Object o);

    public static native long measureNanoTime(Runnable block);
    public static native long measureTimeMicros(Runnable block);
    public static native long measureTimeMillis(Runnable block);


}
