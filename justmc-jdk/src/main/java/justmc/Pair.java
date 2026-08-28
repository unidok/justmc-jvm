package justmc;

import justmc.annotation.Inline;
import org.jetbrains.annotations.NotNull;

@Inline
public final class Pair<A extends Primitive, B extends Primitive> extends Primitive {
    public final A first;
    public final B second;

    public Pair(A first, B second) {
        this.first = first;
        this.second = second;
    }

    @NotNull
    public static Pair<Text, Primitive> of(String first, Primitive second) {
        return new Pair<>(Text.plain(first), second);
    }
}
