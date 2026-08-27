package justmc;

public abstract class PrimitiveError extends Throwable {
    protected PrimitiveError() {}

    public abstract Text getId();

    public abstract Text getDisplay();
}
