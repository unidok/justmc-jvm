package justmc;

import justmc.annotation.MustBeConst;

/**
 * Класс для маркеров (перечислений).
 */
public abstract class EnumPrimitive extends Primitive {
    protected EnumPrimitive() {}

    /**
     * Получает маркер по имени.
     * Регистр имени зависит от того, используется ли он в игровых значениях.
     * В игровых значениях - нижний регистр, в действиях - верхний (но нижний тоже работает).
     * @param name Идентификатор маркера
     * @return Маркер true/false
     */
    public static native EnumPrimitive of(@MustBeConst String name);

    public static native EnumPrimitive of(Variable linkedVariable, @MustBeConst String defaultValue);

    /**
     * Получает маркер true/false.
     * @param b Логическое значение
     * @return Маркер true/false
     */
    public static native EnumPrimitive of(boolean b);
}
