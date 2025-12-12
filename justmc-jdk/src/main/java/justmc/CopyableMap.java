package justmc;

public sealed class CopyableMap permits MutableCopyableMap {
    public static native CopyableMap of(CopyableList keys, CopyableList values);

    public native Object get(Object key);
    public native Object get(Object key, Object defaultValue);
}
