package justmc;

import justmc.annotation.Inline;

@Inline
public final class Block extends Primitive {
    private Block() {}

    /**
     * Получает блок по данным.
     * @param blockData Данные блока
     * @return Блок
     */
    public static native Block of(String blockData);
}
