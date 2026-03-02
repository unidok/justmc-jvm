package justmc;

import justmc.annotation.PrimitiveType;

@PrimitiveType
public final class Vector {
    private Vector() {}

    public static native Vector of(double x, double y, double z);

    public native double getX();
    public native double getY();
    public native double getZ();
}
