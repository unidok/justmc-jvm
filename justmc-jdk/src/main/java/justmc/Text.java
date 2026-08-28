package justmc;

import justmc.annotation.Inline;
import justmc.annotation.MustBeConst;
import justmc.enums.TextParsing;
import org.jetbrains.annotations.NotNull;

@Inline
public final class Text extends Primitive {
    private Text() {}

    @NotNull
    public static native Text plain(String text);

    @NotNull
    public static native Text legacy(String text);

    @NotNull
    public static native Text mini(String text);

    @NotNull
    public static native Text json(String text);

//    @NotNull
//    public static native Text withArgs(@MustBeConst String pattern, Primitive... args);

    public int getLength() {
        var result = Variable.result();
        Unsafe.operation("set_variable_text_length", MapPrimitive.of(
                Pair.of("variable", result),
                Pair.of("text", this)
        ));
        return Unsafe.asInt(result);
    }

    @NotNull
    public Text setParsing(TextParsing parsing) {
        var result = Variable.result();
        Unsafe.operation("set_variable_change_component_parsing", MapPrimitive.of(
                Pair.of("variable", result),
                Pair.of("component", this),
                Pair.of("parsing", parsing)
        ));
        return Unsafe.cast(result);
    }

    @NotNull
    public native Text plus(Text other);

    @NotNull
    public native Text append(Text other);
}
