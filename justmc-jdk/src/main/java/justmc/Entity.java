package justmc;

import justmc.annotation.PrimitiveType;

@PrimitiveType
public enum Entity {
    CURRENT,
    DEFAULT,
    KILLER,
    DAMAGER,
    SHOOTER,
    PROJECTILE,
    VICTIM,
    RANDOM,
    ALL_MOBS,
    ALL,
    LAST;

    public native void operation(String id);
    public native void operation(String id, CopyableMap args);
}
