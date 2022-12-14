package io.javadb.data;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

import java.util.*;

public class LRUCache<K, V> {
    private final BiMap<K, V> listNodeMap;
    private final LinkedList<V> linkedList;
    private final int capacity;

    public LRUCache(int capacity) {
        this.capacity = capacity;
        this.listNodeMap = HashBiMap.create(new HashMap<>(capacity));
        this.linkedList = new LinkedList<>();
    }

    public Optional<V> get(K key) {
        V listNode = this.listNodeMap.get(key);
        if (listNode != null) {
            linkedList.remove(listNode);
            linkedList.add(listNode);
            return Optional.of(listNode);
        }
        return Optional.empty();
    }

    public boolean put(K key, V value) {
        if (this.listNodeMap.containsKey(key)) {
            V item = this.listNodeMap.get(key);
            if (!linkedList.remove(item)) {
                return false;
            }
            linkedList.add(value);
        } else {
            if (this.size() >= this.capacity) {
                this.evictElement();
            }
            this.linkedList.addLast(value);
        }
        this.listNodeMap.put(key, value);
        return true;
    }

    public void evictElement() {
        V remove = this.linkedList.removeFirst();
        listNodeMap.inverse().remove(remove);
    }

    public int size() {
        return this.listNodeMap.size();
    }


}
