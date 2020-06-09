package com.youthlin.leetcode.graph;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author youthlin.chen @ 2020-06-09 22:27:20
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StandardVertex<K, V> implements Vertex<K, V> {
    K key;
    V value;

    @Override
    public V setValue(V value) {
        V old = this.value;
        this.value = value;
        return old;
    }
}
