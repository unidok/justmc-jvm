package justmc;

import justmc.annotation.Inline;

@Inline
public final class Block extends Primitive {
    private Block() {}

    /**
     * Получает блок по данным.
     * @param blockData Данные блока
     * @return Блок
     * @see <a href=https://github.com/donzgold/JustMC_compilator/blob/master/data/blocks.json>Список блоков</a>
     */
    public static native Block of(String blockData);
}
