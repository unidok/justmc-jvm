package justmc.enums;

import justmc.EnumPrimitive;
import justmc.Unsafe;

public final class TextMerging extends EnumPrimitive {
    public static final TextMerging CONCATENATION = Unsafe.cast(EnumPrimitive.of("CONCATENATION"));
    public static final TextMerging SEPARATE_LINES = Unsafe.cast(EnumPrimitive.of("SEPARATE_LINES"));
    public static final TextMerging SPACES = Unsafe.cast(EnumPrimitive.of("SPACES"));

    public static final class ActionBar extends EnumPrimitive {
        public static final ActionBar CONCATENATION = Unsafe.cast(EnumPrimitive.of("CONCATENATION"));
        public static final ActionBar SPACES = Unsafe.cast(EnumPrimitive.of("SPACES"));

        private ActionBar() {}
    }

    private TextMerging() {}
}
