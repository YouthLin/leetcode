package com.youthlin.leetcode.graph;

import lombok.Data;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 邻接表
 * 稀疏图、顶点较多，即图结构比较大时，更适宜选择邻接表
 * <p>
 * 默认是有向图
 *
 * @author youthlin.chen @ 2020-06-09 20:29:51
 */
@Data
public abstract class AdjacencyList<T> implements Graph<T> {
    /*** 存储每个顶点出发的边 */
    protected final Map<Vertex<T>, Set<Edge<T>>> map = new LinkedHashMap<>();

    @Override
    public int vertexSize() {
        return map.size();
    }

    protected int valueSize() {
        return map.values()
                .stream()
                .mapToInt(Set::size)
                .sum();
    }

    @Override
    public boolean addVertex(Vertex<T> vertex) {
        if (map.containsKey(vertex)) {
            return false;
        }
        map.put(vertex, new HashSet<>());
        return true;
    }

    @Override
    public Iterable<Edge<T>> edges(Vertex<T> vertex) {
        return getOrEmpty(vertex);
    }

    @Override
    public Iterable<Edge<T>> edges() {
        return map.values()
                .stream()
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }

    protected Set<Edge<T>> getOrCreate(Vertex<T> vertex) {
        return map.computeIfAbsent(vertex, v -> new HashSet<>());
    }

    protected Set<Edge<T>> getOrEmpty(Vertex<T> vertex) {
        return map.getOrDefault(vertex, Collections.emptySet());
    }

    protected boolean addEdgeTo(Vertex<T> vertex, Edge<T> edge) {
        return getOrCreate(vertex).add(edge);
    }

    protected boolean removeEdgeFrom(Vertex<T> vertex, Edge<T> edge) {
        Set<Edge<T>> adjacency = getOrEmpty(edge.getFrom());
        if (adjacency.isEmpty()) {
            return false;
        }
        return adjacency.remove(edge);
    }

    protected static boolean checkState(boolean first, boolean second) {
        if (first != second) {
            throw new IllegalStateException();
        }
        return first;
    }

    /*** 有向图 */
    private static class Directed<T> extends AdjacencyList<T> {

        @Override
        public int edgeSize() {
            return valueSize();
        }

        @Override
        public boolean addEdge(Edge<T> edge) {
            return addEdgeTo(edge.getFrom(), edge);
        }

        @Override
        public boolean removeEdge(Edge<T> edge) {
            return removeEdgeFrom(edge.getFrom(), edge);
        }

    }

    /*** 无向图 */
    private static class UnDirected<T> extends AdjacencyList<T> {

        @Override
        public int edgeSize() {
            return valueSize() / 2;
        }

        @Override
        public boolean addEdge(Edge<T> edge) {
            boolean addFrom = addEdgeTo(edge.getFrom(), edge);
            boolean addTo = addEdgeTo(edge.getTo(), edge.reverse());
            return checkState(addFrom, addTo);
        }

        @Override
        public boolean removeEdge(Edge<T> edge) {
            boolean remove1 = removeEdgeFrom(edge.getFrom(), edge);
            boolean remove2 = removeEdgeFrom(edge.getTo(), edge.reverse());
            return checkState(remove1, remove2);
        }

    }

    public static <T> AdjacencyList<T> directed() {
        return new Directed<>();
    }

    public static <T> AdjacencyList<T> unDirected() {
        return new UnDirected<>();
    }

    public static <T> AdjacencyList<T> of(boolean directed,
            List<? extends Vertex<T>> vertexList,
            List<? extends Edge<T>> edgeList) {
        AdjacencyList<T> graph;
        if (directed) {
            graph = new Directed<>();
        } else {
            graph = new UnDirected<>();
        }
        for (Vertex<T> vertex : vertexList) {
            graph.addVertex(vertex);
        }
        for (Edge<T> edge : edgeList) {
            graph.addEdge(edge);
        }
        return graph;
    }

}
