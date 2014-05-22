package treeset.extensions.collections;

/**
 * Interface representing cachable object. This interface is required to ensure internal cache integrity and optimal complexity.
 *
 * Created by daemontus on 16/04/14.
 */
public interface Cacheable<K> {
    K getKey();
}
