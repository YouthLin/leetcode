package com.youthlin.leetcode.graph;

/**
 * 图的边
 *
 * @author youthlin.chen @ 2020-06-09 20:46:47
 */
public interface Edge<K, V> {
    Vertex<K, V> getFrom();

    Vertex<K, V> getTo();
}
