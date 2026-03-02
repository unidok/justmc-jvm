package justmc;

import justmc.annotation.Destructure;
import justmc.annotation.PrimitiveTemplate;
import justmc.annotation.PrimitiveType;

@PrimitiveType
public final class CopyableList<@PrimitiveTemplate E> {
    public static final int MAX_SIZE = 20000;

    private CopyableList() {}

    @SafeVarargs
    public static native <E> CopyableList<E> of(E... values);

    public native int size();

    public native E get(int index);

    public native E get(int index, E defaultValue);

    public native int indexOf(E value);

    public native int lastIndexOf(E value);

    public native CopyableList<E> add(E value);

    public native CopyableList<E> add(int index, E value);

    public native CopyableList<E> addAll(CopyableList<E> list);

    public native CopyableList<E> set(int index, E value);

    public native CopyableList<E> remove(E value);

    public native CopyableList<E> removeLast(E value);

    public native CopyableList<E> removeAll(E value);

    public native @Destructure Pair<E, CopyableList<E>> removeAt(int index);

    public native CopyableList<E> shuffled();

    public native CopyableList<E> reversed();

    public native CopyableList<E> toSet();

    public native CopyableList<E> subList(int begin, int end);

    public native CopyableList<E> sorted();

    public native CopyableList<E> sortedByDescending();

    public native CopyableList<E> flatten(boolean deep);

}
