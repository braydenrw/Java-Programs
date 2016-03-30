/**
 * Created by braydenrw on 10/3/14.
 */
public interface SplayTree<K extends Comparable<K>, V> {
    public void insert(K key, V val);
    public V search(K key);
    public V remove(K key);
}
