package justmc;

public final class Text {
    private Text() {}

    public static native Text plain(String text);
    public static native Text legacy(String text);
    public static native Text mini(String text);
    public static native Text json(String text);
}
