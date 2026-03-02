package justmc;

import org.jetbrains.annotations.NotNull;

public final class Util {
    private Util() {}

    public static native void operation(String id);
    public static native void operation(String id, CopyableMap<String, Object> args);

    public static native boolean asBoolean(Object o);
    public static native byte asByte(Object o);
    public static native short asShort(Object o);
    public static native char asChar(Object o);
    public static native int asInt(Object o);
    public static native long asLong(Object o);
    public static native float asFloat(Object o);
    public static native double asDouble(Object o);
    public static native @NotNull String asString(Object o);
    public static native @NotNull Text asText(Object o);
    public static native @NotNull Vector asVector(Object o);
    public static native @NotNull Location asLocation(Object o);
    public static native @NotNull Block asBlock(Object o);
    public static native @NotNull Item asItem(Object o);
    public static native <E> @NotNull CopyableList<E> asCopyableList(Object o);
    public static native <E> @NotNull MutableList<E> asMutableList(Object o);
    public static native <K, V> @NotNull CopyableMap<K, V> asCopyableMap(Object o);
    public static native <K, V> @NotNull MutableMap<K, V> asMutableMap(Object o);

    public static native boolean hasClass(Object o);
    public static native void delete(Object o);

    public static native long measureNanoTime(Runnable block);
    public static native long measureTimeMicros(Runnable block);
    public static native long measureTimeMillis(Runnable block);


}
