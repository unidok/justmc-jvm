package justmc.entity;

import justmc.*;
import justmc.annotation.FakeObject;
import justmc.enums.NbtValueType;

/**
 * Самая абстрактная сущность, которая может быть игроком
 */
@FakeObject
public interface AbstractEntity {
    /**
     * Получает игровое значение с селектором.
     * @param id Идентификатор значения
     * @return Игровое значение с селектором
     * @see <a href="https://github.com/donzgold/JustMC_compilator/blob/master/data/values.json">Список значений</a>
     */
    Primitive getValue(String id);

    /**
     * Вызов действия Creative+ с селектором.
     * @param id Идентификатор действия
     * @param args Аргументы действия
     * @see <a href="https://github.com/donzgold/JustMC_compilator/blob/master/data/actions.json">Список действий</a>
     */
    void operation(String id, MapPrimitive<Text, Primitive> args);

    /**
     * @param id Идентификатор условия
     * @param args Аргументы условия
     * @return Условие с селектором
     * @see <a href="https://github.com/donzgold/JustMC_compilator/blob/master/data/actions.json">Список действий</a>
     */
    Conditional conditional(String id, MapPrimitive<Text, Primitive> args);

    default Text getName() {
        return Unsafe.cast(getValue("name"));
    }

    default Location getLocation() {
        return Unsafe.cast(getValue("location"));
    }

    default double getX() {
        return Unsafe.asDouble(getValue("x_coordinate"));
    }

    default double getY() {
        return Unsafe.asDouble(getValue("y_coordinate"));
    }

    default double getZ() {
        return Unsafe.asDouble(getValue("z_coordinate"));
    }

    default <T> T getNbt(ListPrimitive<Text> path, NbtValueType<T> valueType) {
        var result = Variable.result();
        operation("entity_get_nbt", MapPrimitive.of(
                Pair.of("variable", result),
                Pair.of("path", path),
                Pair.of("value_type", valueType)
        ));
        return Unsafe.cast(result);
    }
}
