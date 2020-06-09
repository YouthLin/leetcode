package com.youthlin.leetcode.graph;

import java.util.Map;

/**
 * 图的边
 *
 * @author youthlin.chen @ 2020-06-09 20:46:47
 */
public interface Edge<K, V> extends Map.Entry<Vertex<K, V>, Vertex<K, V>> {
    Vertex<K, V> getFrom();

    Vertex<K, V> getTo();

    @Override
    default Vertex<K, V> getKey() {
        return getFrom();
    }

    @Override
    default Vertex<K, V> getValue() {
        return getTo();
    }

    @Override
    default Vertex<K, V> setValue(Vertex<K, V> value) {
        throw new UnsupportedOperationException();
    }
}
