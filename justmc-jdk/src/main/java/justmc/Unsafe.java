package justmc;

import justmc.annotation.FakeObject;
import justmc.annotation.Inline;

import java.util.Iterator;

/**
 * Класс с утилитами для кода и взаимодействия с действиями Creative+.
 * Внимание! Использование методов отсюда может привести к
 * неопределённому поведению или ошибкам в вашем коде.
 * Используйте, только если знаете, что делаете.
 */
@Inline
public final class Unsafe {
    private Unsafe() {}

    /**
     * Вызов действия Creative+.
     * @param id Идентификатор действия
     * @param args Аргументы действия
     * @see <a href="https://github.com/donzgold/JustMC_compilator/blob/master/data/actions.json">Список действий</a>
     */
    public static native void operation(String id, MapPrimitive<Text, Primitive> args);

    /**
     * Вызов действия Creative+ с указанием блока кода.
     * Например, можно использовать для действий из контроллера или для циклов.
     * @param id Идентификатор действия
     * @param args Аргументы действия
     * @param block Блок кода
     * @see <a href="https://github.com/donzgold/JustMC_compilator/blob/master/data/actions.json">Список действий</a>
     */
    public static native void operation(String id, MapPrimitive<Text, Primitive> args, Runnable block);

    /**
     * Вызов действия Creative+ с указанием условия.
     * Например, можно использовать для выборки по условию.
     * @param id Идентификатор действия
     * @param conditional Условие действия
     * @see <a href="https://github.com/donzgold/JustMC_compilator/blob/master/data/actions.json">Список действий</a>
     */
    public static native void operation(String id, Conditional conditional);

    /**
     * Вызов действия Creative+ с указанием условия и блока кода.
     * Например, можно использовать для условий.
     * @param id Идентификатор действия
     * @param conditional Аргументы действия
     * @param block Блок кода
     * @see <a href="https://github.com/donzgold/JustMC_compilator/blob/master/data/actions.json">Список действий</a>
     */
    public static native void operation(String id, Conditional conditional, Runnable block);

    /**
     * Создаёт примитивный итератор, который будет заменён на цикл.
     * Требуется строгий порядок действий:
     * <pre>{@code
     * Iterator<Primitive> iterator = Unsafe.iterator(...);
     * while (iterator.hasNext()) {
     *     Primitive n = iterator.next();
     *     // Последующий код, где нельзя взаимодействовать с итератором
     * }
     * }</pre>
     * Если использовать это не по шаблону, показанному выше, то это может привести к ошибкам на этапе трансляции.
     * @param id Идентификатор цикла
     * @param args Аргументы цикла
     * @return Примитивный итератор
     * @see <a href="https://github.com/donzgold/JustMC_compilator/blob/master/data/actions.json">Список действий</a>
     */
    @FakeObject
    public static native <E extends Primitive> Iterator<E> iterator(String id, MapPrimitive<Text, Primitive> args);

    /**
     * Принимает значение как boolean
     * @param o Значение
     * @return То же самое значение, но с типом boolean
     */
    public static native boolean asBoolean(Primitive o);

    /**
     * Принимает значение как byte
     * @param o Значение
     * @return То же самое значение, но с типом byte
     */
    public static native byte asByte(Primitive o);

    /**
     * Принимает значение как short
     * @param o Значение
     * @return То же самое значение, но с типом short
     */
    public static native short asShort(Primitive o);

    /**
     * Принимает значение как char
     * @param o Значение
     * @return То же самое значение, но с типом char
     */
    public static native char asChar(Primitive o);

    /**
     * Принимает значение как int
     * @param o Значение
     * @return То же самое значение, но с типом int
     */
    public static native int asInt(Primitive o);

    /**
     * Принимает long как int
     * @param l Значение
     * @return То же самое значение, но с типом int
     */
    public static native int asInt(long l);

    /**
     * Принимает значение как long
     * @param o Значение
     * @return То же самое значение, но с типом long
     */
    public static native long asLong(Primitive o);

    /**
     * Принимает int как long
     * @param i Значение
     * @return То же самое значение, но с типом long
     */
    public static native long asLong(int i);

    /**
     * Принимает значение как float
     * @param o Значение
     * @return То же самое значение, но с типом float
     */
    public static native float asFloat(Primitive o);

    /**
     * Принимает значение как double
     * @param o Значение
     * @return То же самое значение, но с типом double
     */
    public static native double asDouble(Primitive o);

    /**
     * Принимает значение как {@code T}
     * @param o Значение
     * @return То же самое значение, но с типом {@code T}
     * @param <T> Новый тип
     */
    public static native <T> T cast(Object o);

    /**
     * Принимает объект как адрес
     * @param o Объект
     * @return То же самое значение, но с типом int
     */
    public static native int asAddress(Object o);

    /**
     * Принимает адрес как объект
     * @param adr Адрес объекта
     * @return То же самое значение, но с типом Object
     */
    public static native Object asObject(int adr);
}