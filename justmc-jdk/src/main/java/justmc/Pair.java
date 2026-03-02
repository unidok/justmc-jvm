package justmc;

public final class Pair<A, B> {
    private Pair() {}

    public static native <A, B> Pair<A, B> of(A a, B b);

    public native A getFirst();
    public native B getSecond();
}
