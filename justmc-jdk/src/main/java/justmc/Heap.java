package justmc;

import justmc.annotation.EventHandler;
import justmc.annotation.Inline;
import justmc.annotation.JmcName;
import justmc.annotation.UnsafeMark;

/**
 * Стандартная реализация кучи, балансирующая между
 * производительностью и максимальным размером.
 * Максимальный размер - 19999 объектов, причём не важно,
 * сколько полей имеет объект. То есть каждый из этих объектов
 * может хранить по 20000 объектных полей и ещё столько же примитивных.
 */
@Inline
@UnsafeMark
public final class Heap {
    private static final int SIZE = ListPrimitive.MAX_SIZE - 1;
    /**
     * Данные кучи, хранящие класс каждого объекта.
     * {@code [null, ссылка на класс, ссылка на класс, ссылка на класс, ...]}
     * Если мы попытаемся обратиться по нулевому указателю, то нам даст null.
     * Мы выделили дополнительную ячейку под null, чтобы избежать лишних проверок.
     * Поэтому размер кучи не 20000, а на 1 меньше.
     */
    private static final ListPrimitive<NumberPrimitive> objs = ListPrimitive.ofNulls(SIZE + 1);
    /**
     * Данные кучи, хранящие количество ссылок на каждой объект.
     * {@code [null, количество ссылок, количество ссылок, количество ссылок, ...]}
     */
    private static final ListPrimitive<NumberPrimitive> refs = ListPrimitive.ofNulls(SIZE + 1);
    /**
     * Очередь свободных указателей.
     * Изначально хранит все указатели.
     */
    private static final ListPrimitive<NumberPrimitive> free = ListPrimitive.empty();
    /**
     * Индекс конца очереди свободных указателей.
     * Как следствие, количество занятой памяти.
     */
    private static int freeHead = 0;
    /**
     * Список объектов, помеченных на удаление.
     */
    private static ListPrimitive<NumberPrimitive> mark = ListPrimitive.empty();

    static {
        // Изначально все указатели свободны:
        for (int i = 1; i <= SIZE; i++) {
            free.add(NumberPrimitive.of(i));
        }
    }

    private Heap() {}

    public static Variable getObjectFieldsVariable(int ptr) {
        return Variable.game(Text.plain("o").plus(NumberPrimitive.of(ptr).asText()));
    }

    public static Variable getPrimitiveFieldsVariable(int ptr) {
        return Variable.game(Text.plain("p").plus(NumberPrimitive.of(ptr).asText()));
    }

    public static int getClass(int ptr) {
        return Unsafe.asInt(objs.get(ptr));
    }

    public static int getRefs(int ptr) {
        return Unsafe.asInt(refs.get(ptr));
    }

    public static void setRefs(int ptr, int r) {
        refs.set(ptr, NumberPrimitive.of(r));
    }

    /**
     * Добавить ссылку на объект.
     * Автоматически вставляется перед каждым дублированием ссылки:
     * при установке в переменные и т.п.
     * @param ptrs Указатель на объект
     */
    @JmcName(name = "ADDREF")
    public static void addRef(int... ptrs) {
        for (int ptr : ptrs) setRefs(ptr, getRefs(ptr) + 1);
    }

    /**
     * Удалить ссылку на объект.
     * Автоматически вставляется после каждой потери ссылки.
     * Если ссылок не осталось, то удаляет объект.
     * @param ptrs Указатель на объект
     * @see #addRef(int... ptr)
     */
    @JmcName(name = "REMREF")
    public static void removeRef(int... ptrs) {
        for (int ptr : ptrs) {
            int refs = getRefs(ptr);
            if (refs >= 1) {
                setRefs(ptr, --refs);
                if (refs == 0) delete(ptr);
            }
        }
    }

    @JmcName(name = "NEW")
    public static int newInstance(int classPtr) {
        if (freeHead >= SIZE) {
            gc(); // Пробуем очистить
            if (freeHead >= SIZE) {
                // Если не смогло очистить, кидаем ошибку
                Thread.fatalError(Text.plain("Out of memory"));
            }
        }
        int ptr = Unsafe.asInt(free.get(freeHead++));
        objs.set(ptr, NumberPrimitive.of(classPtr));
        return ptr;
    }

    @JmcName(name = "DELETE")
    public static void delete(int ptr) {
        free.set(--freeHead, NumberPrimitive.of(ptr));
        if (getObjectFieldsVariable(ptr).exists()) {
            for (NumberPrimitive field : Unsafe.<ListPrimitive<NumberPrimitive>>cast(getObjectFieldsVariable(ptr))) {
                int r = getRefs(Unsafe.asInt(field)) - 1;
                setRefs(Unsafe.asInt(field), r);
                if (r == 0) {
                    mark.add(field);
                }
            }
        }
        Variable.purge(ListPrimitive.of(
                getObjectFieldsVariable(ptr).getName(),
                getPrimitiveFieldsVariable(ptr).getName()
        ));
    }

    @EventHandler(id = "world_start")
    private static void cleaner() {
        while (true) {
            Thread.wait(100);
            gc();
        }
    }

    @JmcName(name = "GC")
    public static void gc() {
        if (mark.isEmpty()) return;
        ListPrimitive<NumberPrimitive> iterable = mark;
        mark = ListPrimitive.empty();
        for (NumberPrimitive ptr : iterable) {
            delete(Unsafe.asInt(ptr));
        }
    }
}
