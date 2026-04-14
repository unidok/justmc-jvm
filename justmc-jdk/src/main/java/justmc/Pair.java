package justmc;

import justmc.annotation.Inline;
import org.jetbrains.annotations.NotNull;

@Inline
public final class Pair<A extends Primitive, B extends Primitive> extends Primitive {
    private Pair() {}

    @NotNull
    public static <A extends Primitive, B extends Primitive> Pair<A, B> of(A a, B b) {
        return Unsafe.cast(ListPrimitive.of(a, b));
    }

    @NotNull
    public static Pair<Text, Primitive> of(String a, Primitive b) {
        return of(Text.plain(a), b);
    }

    public A getFirst() {
        return Unsafe.cast(Unsafe.<ListPrimitive<?>>cast(this).get(0));
    }

    public B getSecond() {
        return Unsafe.cast(Unsafe.<ListPrimitive<?>>cast(this).get(1));
    }
}
