package justmc;

import justmc.enums.TextMerging;
import justmc.annotation.PrimitiveType;

@PrimitiveType
public enum Player {
    CURRENT,
    DEFAULT,
    KILLER,
    DAMAGER,
    SHOOTER,
    VICTIM,
    RANDOM,
    ALL;

    public native void operation(String id);
    public native void operation(String id, CopyableMap<String, Object> args);

    public native void sendMessage(String message);
    public native void sendMessage(Text[] message, TextMerging merging);

    public native Location getLocation();
    public native double getX();
    public native double getY();
    public native double getZ();
    public native Location getTargetBlock();
}