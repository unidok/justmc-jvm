package justmc;
import justmc.annotation.PrimitiveTemplate;
import justmc.annotation.PrimitiveType;

@PrimitiveType
public final class CopyableMap<@PrimitiveTemplate K, @PrimitiveTemplate V> {
    public static final int MAX_SIZE = 10000;

    private CopyableMap() {}

    public static native <K, V> CopyableMap<K, V> of(CopyableList<K> keys, CopyableList<V> values);

    public native int size();

    public native V get(K key);

    public native V get(K key, V defaultValue);

    public native V getByIndex(int index, V defaultValue);

    public native CopyableMap<K, V> put(K key, V value);

    public native CopyableMap<K, V> putAll(CopyableMap<K, V> map);

    public native CopyableMap<K, V> remove(K key);

    @SafeVarargs
    public final native CopyableMap<K, V> remove(K key, V... values);

    public native CopyableList<K> getKeys();

    public native CopyableList<V> getValues();

    public native K getKeyByValue(V value, V defaultValue);

    public native K getLastKeyByValue(V value, V defaultValue);

    public native CopyableList<K> getAllKeysByValue(V value, V defaultValue);

    public native K getKeyByIndex(int index, K defaultValue);

    public native CopyableMap<K, V> sortedByKeys();

    public native CopyableMap<K, V> sortedByKeysByDescending();

    public native CopyableMap<K, V> sortedByValues();

    public native CopyableMap<K, V> sortedByValuesByDescending();

}
