package justmc;

public final class PrimitiveError extends Primitive {
    public final Text id;

    public PrimitiveError(Text id) {
        this.id = id;
    }

    public PrimitiveError(String id) {
        this.id = Text.plain(id);
    }
}
