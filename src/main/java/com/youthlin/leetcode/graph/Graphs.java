package com.youthlin.leetcode.graph;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Objects;
import java.util.Queue;
import java.util.Set;
import java.util.function.Consumer;

/**
 * @author youthlin.chen @ 2020-06-09 21:42:40
 */
public class Graphs {
    /*** 广度优先 */
    public static <K, V> void bfs(Graph<K, V> graph,
            Vertex<K, V> start,
            Consumer<Vertex<K, V>> action) {
        Objects.requireNonNull(graph);
        Objects.requireNonNull(start);
        Objects.requireNonNull(action);
        Queue<Vertex<K, V>> queue = new LinkedList<>();
        Set<Vertex<K, V>> visited = new HashSet<>();
        Vertex<K, V> vertex = start;
        queue.add(vertex);
        while (!queue.isEmpty()) {
            vertex = queue.poll();
            if (!visited.contains(vertex)) {
                action.accept(vertex);
                visited.add(vertex);
            }
            for (Edge<K, V> edge : graph.getEdges(vertex)) {
                if (!visited.contains(edge.getTo())) {
                    queue.offer(edge.getTo());
                }
            }
        }
    }

}
