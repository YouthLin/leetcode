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
public class StandardEdge<K, V> implements Edge<K, V> {
    Vertex<K, V> from;
    Vertex<K, V> to;
}
