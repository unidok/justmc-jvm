package justmc;

import justmc.enums.TextMerging;

public enum Player {
    CURRENT,
    DEFAULT,
    KILLER,
    DAMAGER,
    SHOOTER,
    VICTIM,
    RANDOM,
    ALL;

    public native void sendMessage(String message);
    public native void sendMessage(Text[] message, TextMerging merging);

    public native Location getLocation();
    public native double getX();
    public native double getY();
    public native double getZ();
    public native Location getTargetBlock();
}