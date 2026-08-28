package justmc;

public final class IndexedValue<T extends Primitive> extends Primitive {
    public final int index;
    public final T value;

    public IndexedValue(int index, T value) {
        this.index = index;
        this.value = value;
    }
}
