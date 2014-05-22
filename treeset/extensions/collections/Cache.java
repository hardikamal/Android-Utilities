package treeset.extensions.collections;

import java.util.Collection;

/**
 * Represents a temporal cache storage. For unspecified number of recently added values,
 * the cache returns them in constant time. However, it is not stated how much instances can
 * the cache hold, or when are they deleted. This is all dependent on actual implementation.
 *
 * Created by daemontus on 13/04/14.
 */

//TODO: rewrite to extend real collection interface
public interface Cache<K, T extends Cacheable<K>>  {

    /**
     * Similar as has(K key), this method checks whether cache contains provided object.
     * As with has(K key), returned value can change at any given moment.
     *
     * @param object object to search for
     * @return true if value with such key is currently in cache
     */
    boolean contains(Cacheable<K> object);

    /**
     * Check whether cache holds value with provided key.
     * But cache can delete any object between has and get calls, so always perform
     * a null check on retrieved data.
     *
     * @param key key identifying value
     * @return true if value with such key is currently in cache
     */
    boolean has(K key);

    /**
     * Get value from cache based on key.
     * @param key key identifying value
     * @return value stored in cache or null if not found
     */
    T get(K key);

    /**
     * Add new value to cache.
     * @param value value to add
     */
    void add(T value);

    /**
     * Add all values from specified collection.
     * @param values values to add
     */
    void addAll(Collection<? extends T> values);

    /**
     * Add all values from specified array.
     * @param values values to add
     */
    <S extends T> void addAll(S[] values);

    /**
     * @return Actual number of currently stored values.
     */
    int size();

    /**
     * Force remove all objects from cache, hence free them for garbage collection.
     */
    void flush();

}
