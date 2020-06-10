package com.youthlin.leetcode.graph;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

/**
 * 图的边
 *
 * @author youthlin.chen @ 2020-06-09 20:46:47
 */
@Data
@AllArgsConstructor(staticName = "of")
@RequiredArgsConstructor(staticName = "of")
public class Edge<T> {
    @NonNull
    private final Vertex<T> from;
    @NonNull
    private final Vertex<T> to;
    private double weight;

    public Edge<T> reverse() {
        return Edge.of(to, from, weight);
    }

}
