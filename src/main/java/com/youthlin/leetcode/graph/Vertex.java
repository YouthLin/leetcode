package com.youthlin.leetcode.graph;

/**
 * 图的顶点
 *
 * @author youthlin.chen @ 2020-06-09 20:44:21
 */
public interface Vertex<K, V> {
    K getKey();

    V getValue();
}
