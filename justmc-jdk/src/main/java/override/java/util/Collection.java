/*
 * Copyright (c) 1997, 2023, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 */

package override.java.util;

public interface Collection<E> extends Iterable<E> {

    java.lang.Object[] toArray();

    int size();

    boolean isEmpty();

    boolean contains(java.lang.Object o);

    java.util.Iterator<E> iterator();

    // Modification Operations

    boolean add(E e);

    boolean remove(java.lang.Object o);

    // Bulk Operations

    boolean containsAll(java.util.Collection<?> c);

    boolean addAll(java.util.Collection<? extends E> c);

    boolean removeAll(java.util.Collection<?> c);

    boolean retainAll(java.util.Collection<?> c);

    void clear();

}
