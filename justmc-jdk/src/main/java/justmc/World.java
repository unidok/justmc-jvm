package justmc;

import justmc.annotation.Destructure;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class World {
    private World() {}

    public static native long currentTimeMillis();
    public static native int getCPU();

    public static native @Destructure RaycastResult raycast(
            @NotNull Location begin,
            double width,
            double length,
            @Nullable CopyableList<String> entities
    );
}
