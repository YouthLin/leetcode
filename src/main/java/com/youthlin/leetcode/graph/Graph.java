package com.youthlin.leetcode.graph;

import java.util.Collection;

/**
 * 图
 *
 * @author youthlin.chen @ 2020-06-09 22:13:22
 */
public interface Graph<K, V> {
    /*** 获取指定顶点的边集合 */
    Collection<Edge<K, V>> getEdges(Vertex<K, V> vertex);

}
