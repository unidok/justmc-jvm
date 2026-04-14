package justmc;

import justmc.annotation.EventHandler;
import justmc.annotation.Inline;

/**
 * Стандартная реализация памяти (кучи), балансирующая между
 * производительностью и максимальным размером.
 * Максимальный размер - 19999 объектов, причём не важно,
 * сколько полей имеет объект. То есть каждый из этих объектов
 * может хранить по 20000 объектных полей и ещё столько же примитивных.
 */
public final class Memory {
    private static final int HEAP_SIZE = ListPrimitive.MAX_SIZE - 1;
    /**
     * Данные кучи, хранящие класс каждого объекта.
     * [null, ссылка на класс, ссылка на класс, ссылка на класс, ...]
     *  ^^^^
     * Если мы попытаемся обратиться по нулевому указателю, то нам даст null.
     * То есть мы выделили дополнительную ячейку под null, чтобы избежать лишних проверок.
     */
    private static ListPrimitive<NumberPrimitive> objs = ListPrimitive.ofNulls(HEAP_SIZE + 1);
    /**
     * Данные кучи, хранящие количество ссылок на каждой объект.
     * [null, количество ссылок, количество ссылок, количество ссылок, ...]
     */
    private static ListPrimitive<NumberPrimitive> refs = ListPrimitive.ofNulls(HEAP_SIZE + 1);
    /**
     * Очередь свободных указателей.
     * Изначально хранит все указатели.
     */
    private static ListPrimitive<NumberPrimitive> free = ListPrimitive.empty();
    /**
     * Индекс начала очереди.
     * По совместительству количество занятой памяти.
     */
    private static int freeHead = 0;
    /**
     * Список объектов, помеченных на удаление.
     */
    private static ListPrimitive<NumberPrimitive> mark = ListPrimitive.empty();

    static {
        // Изначально все указатели свободны:
        for (int i = 1; i <= HEAP_SIZE; i++) {
            free = free.add(NumberPrimitive.of(i));
        }
    }

    private Memory() {}

    @Inline
    public static Variable getObjectFieldsVariable(int ptr) {
        return Variable.game(Text.plain("o").plus(NumberPrimitive.of(ptr)));
    }

    @Inline
    public static Variable getPrimitiveFieldsVariable(int ptr) {
        return Variable.game(Text.plain("p").plus(NumberPrimitive.of(ptr)));
    }

    @Inline
    public static int getClass(int ptr) {
        return Unsafe.asInt(objs.get(ptr));
    }

    @Inline
    public static int getRefs(int ptr) {
        return Unsafe.asInt(refs.get(ptr));
    }

    @Inline
    public static void setRefs(int ptr, int r) {
        refs = refs.set(ptr, NumberPrimitive.of(r));
    }

    /**
     * Добавить ссылку на объект.
     * Автоматически вставляется перед каждым дублированием ссылки:
     * при передаче аргументов, при установке в переменные и т.п.
     * @param ptr Указатель на объект
     */
    @Inline
    public static void addRef(int ptr) {
        setRefs(ptr, getRefs(ptr) + 1);
    }

    /**
     * Удалить ссылку на объект.
     * Автоматически вставляется после каждого дублирования ссылки.
     * Если ссылок не осталось, то удаляет объект.
     * @param ptr Указатель на объект
     * @see #addRef(int ptr)
     */
    public static void removeRef(int ptr) {
        int refs = getRefs(ptr);
        if (refs >= 1) {
            setRefs(ptr, --refs);
            if (refs == 0) delete(ptr);
        }
    }

    public static int newInstance(Class<?> clazz) {
        if (freeHead >= HEAP_SIZE) {
            gc(); // Пробуем очистить
            if (freeHead >= HEAP_SIZE) {
                // Если не смогло очистить, кидаем ошибку
                Thread.fatalError(Text.plain("Out of memory"));
            }
        }
        int ptr = Unsafe.asInt(free.get(freeHead++));
        objs = objs.set(ptr, NumberPrimitive.of(Unsafe.asAddress(clazz)));
        return ptr;
    }

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
            Thread.wait(93);
            gc();
        }
    }

    public static void gc() {
        if (mark.isEmpty()) return;
        ListPrimitive<NumberPrimitive> iterable = mark;
        mark = ListPrimitive.empty();
        for (NumberPrimitive ptr : iterable) {
            delete(Unsafe.asInt(ptr));
        }
    }
}
