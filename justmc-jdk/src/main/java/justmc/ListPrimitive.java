package justmc;

import justmc.annotation.Inline;
import org.jetbrains.annotations.NotNull;

import java.util.Iterator;

@Inline
public final class ListPrimitive<E extends Primitive> extends Primitive implements Iterable<E> {
    public static final int MAX_SIZE = 20000;

    private ListPrimitive() {}

    @NotNull
    public static native <E extends Primitive> ListPrimitive<E> empty();

    @NotNull
    public static native ListPrimitive<NumberPrimitive> ofNulls(int size);

    @NotNull
    @SafeVarargs
    public static native <E extends Primitive> ListPrimitive<E> of(E... values);

    @NotNull
    public static ListPrimitive<NumberPrimitive> of(boolean... arr) {
        return of(Unsafe.<NumberPrimitive[]>cast(arr));
    }

    @NotNull
    public static ListPrimitive<NumberPrimitive> of(byte... arr) {
        return of(Unsafe.<NumberPrimitive[]>cast(arr));
    }

    @NotNull
    public static ListPrimitive<NumberPrimitive> of(char... arr) {
        return of(Unsafe.<NumberPrimitive[]>cast(arr));
    }

    @NotNull
    public static ListPrimitive<NumberPrimitive> of(short... arr) {
        return of(Unsafe.<NumberPrimitive[]>cast(arr));
    }

    @NotNull
    public static ListPrimitive<NumberPrimitive> of(int... arr) {
        return of(Unsafe.<NumberPrimitive[]>cast(arr));
    }

    @NotNull
    public static ListPrimitive<NumberPrimitive> of(long... arr) {
        return of(Unsafe.<NumberPrimitive[]>cast(arr));
    }

    @NotNull
    public static ListPrimitive<NumberPrimitive> of(float... arr) {
        return of(Unsafe.<NumberPrimitive[]>cast(arr));
    }

    @NotNull
    public static ListPrimitive<NumberPrimitive> of(double... arr) {
        return of(Unsafe.<NumberPrimitive[]>cast(arr));
    }

    public native int size();

    public native E get(int index);

    public native E get(int index, E defaultValue);

    @NotNull
    public native ListPrimitive<E> set(int index, E value);

    @NotNull
    public native ListPrimitive<E> add(E value);

    @NotNull
    public native ListPrimitive<E> addAll(ListPrimitive<E> value);

    public native boolean isEmpty();

    public native boolean contains(E value);

    public native int indexOf(E value);

    public native int lastIndexOf(E value);

    @NotNull
    public ListPrimitive<E> subList(int start, int end) {
        var result = Variable.result();
        Unsafe.operation("set_variable_trim_list", MapPrimitive.of(
                Pair.of("variable", result),
                Pair.of("list", this),
                Pair.of("start", NumberPrimitive.of(start)),
                Pair.of("end", NumberPrimitive.of(end))
        ));
        return Unsafe.cast(result);
    }

    @NotNull
    @Override
    public Iterator<E> iterator() {
        return Unsafe.iterator("repeat_for_each_in_list", MapPrimitive.of(
                Pair.of("value_variable", Variable.temp()),
                Pair.of("list", this)
        ));
    }

    @NotNull
    public Iterator<IndexedValue<E>> indexedIterator() {
        return Unsafe.iterator("repeat_for_each_in_list", MapPrimitive.of(
                Pair.of("index_variable", Variable.temp()),
                Pair.of("value_variable", Variable.temp()),
                Pair.of("list", this)
        ));
    }

    @NotNull
    public Primitive[] toArray() {
        int ptr = Heap.newInstance(Unsafe.asAddress(Primitive[].class));
        Heap.getPrimitiveFieldsVariable(ptr).setValue(this);
        return Unsafe.cast(Unsafe.asObject(ptr));
    }

    @NotNull
    public ListPrimitive<Text> asTexts() {
        return Unsafe.cast(this);
    }
}
