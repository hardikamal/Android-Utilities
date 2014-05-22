package treeset.extensions.collections;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by daemontus on 16/04/14.
 */
public class MemoryCache<K, T extends Cacheable<K>> implements Cache<K, T> {

    private static final Object LOCK = new Object();

    private Cacheable<K>[] data;
    private Map<K, Integer> indexes;
    private int pointer;

    public MemoryCache() {
        this(100);
    }

    @SuppressWarnings("unchecked")
    public MemoryCache(int capacity) {
        data = (Cacheable<K>[]) new Cacheable[capacity];
        indexes = new HashMap<K, Integer>();
    }

    @Override
    public boolean contains(Cacheable<K> object) {
        return object != null && has(object.getKey());
    }

    @Override
    public boolean has(K key) {
        if (key == null) {
            return false;
        }
        Integer index = indexes.get(key);
        return !(index == null || index >= data.length) && key.equals(data[index].getKey());
    }

    @Override
    @SuppressWarnings("unchecked")  //because all data in array are inserted in add, which accepts only T values
    public T get(K key) {
        if (key == null) {
            return null;
        }
        synchronized (LOCK) {   //prevent array size changes
            Integer index = indexes.get(key);
            if (index == null || index >= data.length) {
                return null;
            }
            if (key.equals(data[index].getKey())) {
                return (T) data[index];
            } else {
                return null;
            }
        }
    }

    @Override
    public void addAll(Collection<? extends T> values) {
        for (T val : values) {
            add(val);
        }
    }

    @Override
    public <Subclass extends T> void addAll(Subclass[] values) {
        for (T val : values) {
            add(val);
        }
    }

    @Override
    public void add(T value) {
        if (value == null) {
            throw new IllegalArgumentException("Cannot add null object to cache.");
        }
        K key = value.getKey();
        if (key == null) {
            throw new IllegalArgumentException("Cannot add object with null key.");
        }
        synchronized (LOCK) {   //prevent flush of values during addition
            if (!has(key)) {
                data[pointer] = value;
                indexes.put(key, pointer);
                pointer = (pointer + 1) % data.length;
            }
        }
    }

    @Override
    public int size() {
        if (indexes.size() >= data.length) {
            return data.length;
        }
        else {
            return indexes.size();
        }
    }

    @Override
    public void flush() {
        for (int i=0; i<data.length; i++) {
            data[i] = null;
        }
    }
}
