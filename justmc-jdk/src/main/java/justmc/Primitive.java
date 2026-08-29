package justmc;

import justmc.annotation.Inline;

/**
 * Класс для примитивов Creative+.<br>
 * Любой наследник этого класса:
 * <ul>
 *     <li>Не имеет полей или все поля примитивные.</li>
 *     <li>Неизменяемый (все поля final).</li>
 *     <li>Копируется по значению.</li>
 * </ul>
 */
@Inline
public abstract class Primitive {
    protected Primitive() {}

    /**
     * Принимает значение как {@link Text}, но никаких преобразований не происходит.
     * Значение само преобразуется в текст, где это необходимо.
     * @return То же самое значение, но с типом {@link Text}
     */
    public final Text asText() {

        return Unsafe.cast(this); // Любое значение можно преобразовать в текст, так что это безопасно
    }

    public final native Text toText();

    @Override
    public final int hashCode() {
        return super.hashCode();
    }

    @Override
    public final boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public final String toString() {
        return Unsafe.cast(new override.java.lang.String(asText()));
    }
}