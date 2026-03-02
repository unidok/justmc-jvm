package justmc;

public final class ThreadLocal {
    public static native void set(String key, Object value);
    public static native Object get(String key);
    public static native void remove(String key);
}
