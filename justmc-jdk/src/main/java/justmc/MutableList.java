package justmc;

import justmc.annotation.PrimitiveTemplate;
import justmc.annotation.PrimitiveType;

@PrimitiveType
public final class MutableList<@PrimitiveTemplate E> {
    public MutableList() {}
    public MutableList(CopyableList<E> list) {}

    public native CopyableList<E> dereference();

    public native int size();

    public native E get(int index);

    public native E get(int index, E defaultValue);

    public native int indexOf(E value);

    public native int lastIndexOf(E value);

    public native void add(E value);

    public native void add(int index, E value);

    public native void addAll(CopyableList<E> list);

    public native void set(int index, E value);

    public native void remove(E value);

    public native void removeLast(E value);

    public native void removeAll(E value);

    public native E removeAt(int index);

    public native void shuffle();

    public native void reverse();

    public native void toSet();

    public native CopyableList<E> subList(int begin, int end);

    public native void sort();

    public native void sortByDescending();

    public native void flatMap(boolean deep);

}
