package justmc;

public sealed class CopyableList permits MutableCopyableList {
    public static native CopyableList of(Object... values);

    public native Object get(int index);
    public native Object get(int index, Object defaultValue);
}
