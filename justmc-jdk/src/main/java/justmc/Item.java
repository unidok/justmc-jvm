package justmc;

import justmc.annotation.Inline;

@Inline
public final class Item extends Primitive {
    private Item() {}

    /**
     * Получает предмет по идентификатору.
     * @param id Идентификатор
     * @return Предмет
     * @see <a href=https://github.com/donzgold/JustMC_compilator/blob/master/data/items.json>Список предметов</a>
     */
    public static native Item of(String id);

    /**
     * Преобразует текст в предмет.
     * @param data Предмет в виде текста
     * @return Предмет
     */
    public static native Item deserialize(String data);

    public int getAmount() {
        var result = Variable.result();
        Unsafe.operation("set_variable_get_item_amount", MapPrimitive.of(
                Pair.of("variable", result),
                Pair.of("item", this)
        ));
        return Unsafe.asInt(result);
    }

    public Item setAmount(int amount) {
        var result = Variable.result();
        Unsafe.operation("set_variable_set_item_amount", MapPrimitive.of(
                Pair.of("variable", result),
                Pair.of("item", this),
                Pair.of("amount", NumberPrimitive.of(amount))
        ));
        return Unsafe.cast(result);
    }

    public Item setName(Text name) {
        var result = Variable.result();
        Unsafe.operation("set_variable_set_item_name", MapPrimitive.of(
                Pair.of("variable", result),
                Pair.of("item", this),
                Pair.of("text", name)
        ));
        return Unsafe.cast(result);
    }

    public Item setLore(ListPrimitive<Text> lore) {
        var result = Variable.result();
        Unsafe.operation("set_variable_set_item_lore", MapPrimitive.of(
                Pair.of("variable", result),
                Pair.of("item", this),
                Pair.of("lore", lore)
        ));
        return Unsafe.cast(result);
    }
}
