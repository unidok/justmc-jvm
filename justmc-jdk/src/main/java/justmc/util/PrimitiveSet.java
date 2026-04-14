//package justmc.util;
//
//import justmc.*;
//import org.jetbrains.annotations.NotNull;
//
//import java.util.Collection;
//import java.util.Iterator;
//import java.util.Set;
//
//public class PrimitiveSet<E extends Primitive> implements Set<E> {
//    public PrimitiveSet() {
//        Unsafe.operation("set_variable_create_map", MapPrimitive.of(
//                Pair.of("variable", delegate())
//        ));
//    }
//
//    private Variable delegate() {
//        return Memory.getPrimitiveFieldsVariable(this);
//    }
//
//    private MapPrimitive<E, ?> map() {
//        return Unsafe.cast(delegate());
//    }
//
//    @Override
//    public int size() {
//        return map().size();
//    }
//
//    @Override
//    public boolean isEmpty() {
//        return map().isEmpty();
//    }
//
//    @Override
//    public boolean contains(Object o) {
//        return map().containsKey(Unsafe.cast(o));
//    }
//
//    @NotNull
//    @Override
//    public Iterator<E> iterator() {
//        return null;
//    }
//
//    @NotNull
//    @Override
//    public Object[] toArray() {
//        return map().getKeys().toArray();
//    }
//
//    @NotNull
//    @Override
//    public <T> T[] toArray(@NotNull T[] a) {
//        return null;
//    }
//
//    @Override
//    public boolean add(E e) {
//        if (map().containsKey(e)) {
//            return false;
//        } else {
//            map().put(e, null);
//            return true;
//        }
//    }
//
//    public void addPrimitive(E e) {
//        map().put(e, null);
//    }
//
//    @Override
//    public boolean remove(Object o) {
//        return false;
//    }
//
//    @Override
//    public boolean containsAll(@NotNull Collection<?> c) {
//        return false;
//    }
//
//    @Override
//    public boolean addAll(@NotNull Collection<? extends E> c) {
//        return false;
//    }
//
//    @Override
//    public boolean retainAll(@NotNull Collection<?> c) {
//        return false;
//    }
//
//    @Override
//    public boolean removeAll(@NotNull Collection<?> c) {
//        return false;
//    }
//
//    @Override
//    public void clear() {
//
//    }
//}
