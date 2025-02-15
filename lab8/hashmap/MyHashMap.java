package hashmap;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 *  A hash table-backed Map implementation. Provides amortized constant time
 *  access to elements via get(), remove(), and put() in the best case.
 *
 *  Assumes null keys will never be inserted, and does not resize down upon remove().
 *  @author YOUR NAME HERE
 */
public class MyHashMap<K, V> implements Map61B<K, V> {

    /**
     * Protected helper class to store key/value pairs
     * The protected qualifier allows subclass access
     */
    protected class Node {
        K key;
        V value;

        Node(K k, V v) {
            key = k;
            value = v;
        }
    }

    /* Instance Variables */
    private Collection<Node>[] buckets;
    private double maxloadfactor;
    private int size;
    // You should probably define some more!

    /** Constructors */
    public MyHashMap() {
        buckets = new Collection[31];
        for (int i = 0; i < buckets.length; i++) {
            buckets[i] = createBucket();
        }
        maxloadfactor = 5.0;
        size = 0;
    }

    public MyHashMap(int initialSize) {
        buckets = new Collection[initialSize];
        for (int i = 0; i < buckets.length; i++) {
            buckets[i] = createBucket();
        }
        maxloadfactor = 5.0;
        size = 0;
    }

    /**
     * MyHashMap constructor that creates a backing array of initialSize.
     * The load factor (# items / # buckets) should always be <= loadFactor
     *
     * @param initialSize initial size of backing array
     * @param maxLoad maximum load factor
     */
    public MyHashMap(int initialSize, double maxLoad) {
        buckets = new Collection[initialSize];
        for (int i = 0; i < buckets.length; i++) {
            buckets[i] = createBucket();
        }
        maxloadfactor = maxLoad;
        size = 0;

    }


    /**
     * Returns a new node to be placed in a hash table bucket
     */
    private Node createNode(K key, V value) {
        return new Node(key, value);
    }

    /**
     * Returns a data structure to be a hash table bucket
     *
     * The only requirements of a hash table bucket are that we can:
     *  1. Insert items (`add` method)
     *  2. Remove items (`remove` method)
     *  3. Iterate through items (`iterator` method)
     *
     * Each of these methods is supported by java.util.Collection,
     * Most data structures in Java inherit from Collection, so we
     * can use almost any data structure as our buckets.
     *
     * Override this method to use different data structures as
     * the underlying bucket type
     *
     * BE SURE TO CALL THIS FACTORY METHOD INSTEAD OF CREATING YOUR
     * OWN BUCKET DATA STRUCTURES WITH THE NEW OPERATOR!
     */
    protected Collection<Node> createBucket() {
        return new LinkedList<>();
    }

    /**
     * Returns a table to back our hash table. As per the comment
     * above, this table can be an array of Collection objects
     *
     * BE SURE TO CALL THIS FACTORY METHOD WHEN CREATING A TABLE SO
     * THAT ALL BUCKET TYPES ARE OF JAVA.UTIL.COLLECTION
     *
     * @param tableSize the size of the table to create
     */
    private Collection<Node>[] createTable(int tableSize) {
        return null;
    }

    // TODO: Implement the methods of the Map61B Interface below
    // Your code won't compile until you do so!

    @Override
    public void clear() {
        buckets = null;
        size = 0;
    }

    @Override
    public boolean containsKey(K k) {
        return findNode(k) != null;
    }

    @Override
    public V get(K key) {
        if (this.containsKey(key)) {
            return findNode(key).value;
        }
        return null;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void put(K key, V value) {
        if (this.containsKey(key)) {
            findNode(key).value = value;
            return;
        }
        int hash = Math.floorMod(key.hashCode(), buckets.length);
        buckets[hash].add(createNode(key, value));
        size++;
        resize();
    }

    @Override
    public Set<K> keySet() {
        Set<K> set = new HashSet<>();
        for (Collection<Node> bucket : buckets) {
            for (Node node : bucket) {
                set.add(node.key);
            }
        }
        return set;
    }

    @Override
    public V remove(K key) {
        if (this.containsKey(key)) {
            int hash = Math.floorMod(key.hashCode(), buckets.length);
            Node node = findNode(key);
            V value = node.value;
            buckets[hash].remove(node);
            size--;
            return value;
        }
        return null;
    }

    @Override
    public V remove(K key, V value) {
        if (this.containsKey(key)) {
            int hash = Math.floorMod(key.hashCode(), buckets.length);
            Node node = findNode(key);
            if (node.value == value) {
                V res = node.value;
                buckets[hash].remove(node);
                size--;
                return res;
            }
        }
        return null;

    }



    private Node findNode(K key) {
        if (buckets == null) return null;
        int hash = Math.floorMod(key.hashCode(), buckets.length);
        for (Node node : buckets[hash]) {
            if (key.equals(node.key)) {
                return node;
            }
        }
        return null;
    }
    private boolean check() {
        return (double)size / (double) buckets.length >= maxloadfactor;
    }
    private void resize() {
        if (check()) {
            Collection<Node>[] newbuckets = new Collection[buckets.length * 2];
            for (int i = 0; i < newbuckets.length; i++) {
                newbuckets[i] = createBucket();
            }
            // I should try using Iterator to do this.
            for (Collection<Node> bucket : buckets) {
                for (Node node : bucket) {
                    int hash = Math.floorMod(node.key.hashCode(), newbuckets.length);
                    newbuckets[hash].add(node);
                }
            }
            buckets = newbuckets;
        }

    }

    public Iterator<K> iterator() {
        return new MapIterator();
    }

    private class MapIterator implements Iterator<K> {

        private List<K> list;

        public MapIterator() {
            list = new LinkedList<>();
          for (Collection<Node> bucket : buckets) {
            for (Node node : bucket) {
              list.add(node.key);
            }
          }
        }

        public boolean hasNext() {
            return !list.isEmpty();
        }

        public K next() {
            if (hasNext()) {
                K res = list.remove(0);
                return res;
            }
            return null;
        }

    }

}
