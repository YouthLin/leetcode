package com.youthlin.leetcode.graph;

import java.util.Map;

/**
 * 图的顶点
 *
 * @author youthlin.chen @ 2020-06-09 20:44:21
 */
public interface Vertex<K, V> extends Map.Entry<K, V> {
    K getKey();

    V getValue();

    @Override
    default V setValue(V value) {
        throw new UnsupportedOperationException();
    }
}
