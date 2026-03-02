package justmc;

import justmc.annotation.PrimitiveType;

@PrimitiveType
public final class Location {
    private Location() {}

    public static native Location of(double x, double y, double z, double yaw, double pitch);
    public static native Location of(double x, double y, double z);

    public native double getX();
    public native double getY();
    public native double getZ();
    public native double getYaw();
    public native double getPitch();
}
