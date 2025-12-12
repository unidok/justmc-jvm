package justmc;

public final class Location {
    private Location() {}

    public static native Location of(double x, double y, double z, float yaw, float pitch);
    public static native Location of(double x, double y, double z);

    public native double getX();
    public native double getY();
    public native double getZ();
    public native float getYaw();
    public native float getPitch();
}
