package justmc;

public final class World {
    private World() {}

    public static native long getTimeMillis();
    public static native int getProcessorUsage();
}
