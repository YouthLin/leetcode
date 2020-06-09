package com.youthlin.leetcode.graph;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author youthlin.chen @ 2020-06-09 20:45:12
 */
@Data
@AllArgsConstructor(staticName = "of")
public class SimpleVertex<K> implements Vertex<K, K> {
    K data;

    @Override
    public K getKey() {
        return data;
    }

    @Override
    public K getValue() {
        return data;
    }

}
