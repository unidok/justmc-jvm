package justmc;

import justmc.annotation.FakeObject;
import justmc.annotation.Inline;
import org.jetbrains.annotations.NotNull;

@Inline
@FakeObject
public final class Conditional {
    private Conditional() {}

    /**
     * @param b Значение условия
     * @return Условие
     * @see <a href="https://github.com/donzgold/JustMC_compilator/blob/master/data/actions.json">Список действий</a>
     */
    @NotNull
    public static native Conditional of(boolean b);

    /**
     * @param id Идентификатор условия
     * @param args Аргументы условия
     * @return Условие
     * @see <a href="https://github.com/donzgold/JustMC_compilator/blob/master/data/actions.json">Список действий</a>
     */
    @NotNull
    public static native Conditional of(String id, MapPrimitive<Text, Primitive> args);

    /**
     * Получает значение условия.
     * Если результат будет использован сразу в {@code if}, то результат может быть встроен.
     * @return Значение условия
     */
    public native boolean get();

    /**
     * Инвертирует условие.
     */
    public native void invert();
}
