package com.youthlin.leetcode.graph;

import lombok.Data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 邻接表
 * 稀疏图、顶点较多，即图结构比较大时，更适宜选择邻接表
 *
 * @author youthlin.chen @ 2020-06-09 20:29:51
 */
@Data
public class AdjacencyList<K, V> implements Graph<K, V> {
    private Map<Vertex<K, V>, List<Vertex<K, V>>> map;

    @Override
    public Collection<Edge<K, V>> getEdges(Vertex<K, V> vertex) {
        List<Vertex<K, V>> list = map.get(vertex);
        return list.stream()
                .map(v -> new StandardEdge<>(vertex, v))
                .collect(Collectors.toList());
    }

    /*** 有向图 */
    public static <K, V> AdjacencyList<K, V> directed(List<? extends Vertex<K, V>> vertexList,
            List<? extends Edge<K, V>> edgeList) {
        return of(true, vertexList, edgeList);
    }

    /*** 无向图 */
    public static <K, V> AdjacencyList<K, V> unDirected(List<? extends Vertex<K, V>> vertexList,
            List<? extends Edge<K, V>> edgeList) {
        return of(false, vertexList, edgeList);
    }

    public static <K, V> AdjacencyList<K, V> of(boolean directed,
            List<? extends Vertex<K, V>> vertexList,
            List<? extends Edge<K, V>> edgeList) {
        AdjacencyList<K, V> adjacencyList = new AdjacencyList<>();
        adjacencyList.map = new LinkedHashMap<>(vertexList.size());
        for (Vertex<K, V> vertex : vertexList) {
            adjacencyList.map.put(vertex, new LinkedList<>());
        }
        for (Edge<K, V> edge : edgeList) {
            Vertex<K, V> from = edge.getFrom();
            Vertex<K, V> to = edge.getTo();
            adjacencyList.map.get(from).add(to);
            if (!directed) {
                adjacencyList.map.get(to).add(from);
            }
        }
        return adjacencyList;
    }

    public static void main(String[] args) {
        // G = {V, E}
        // V = {1,2,3,4,5}
        // E = {(1,2),(1,3),(1,4),(2,3),(3,4),(3,5)}
        SimpleVertex<Integer> one = SimpleVertex.of(1);
        SimpleVertex<Integer> two = SimpleVertex.of(2);
        SimpleVertex<Integer> three = SimpleVertex.of(3);
        SimpleVertex<Integer> four = SimpleVertex.of(4);
        SimpleVertex<Integer> five = SimpleVertex.of(5);
        List<SimpleVertex<Integer>> vertexList = new ArrayList<>();
        vertexList.add(one);
        vertexList.add(two);
        vertexList.add(three);
        vertexList.add(four);
        vertexList.add(five);
        List<SimpleEdge<Integer>> edgeList = new ArrayList<>();
        edgeList.add(SimpleEdge.of(one, two));
        edgeList.add(SimpleEdge.of(one, three));
        edgeList.add(SimpleEdge.of(one, four));
        edgeList.add(SimpleEdge.of(two, three));
        edgeList.add(SimpleEdge.of(three, one));
        edgeList.add(SimpleEdge.of(three, five));
        edgeList.add(SimpleEdge.of(four, three));

        // 有向图
        // 1: 2->3->4
        // 2: 3
        // 3: 1->5
        // 4: 3
        // 5:
        AdjacencyList<Integer, Integer> directed = directed(vertexList, edgeList);
        System.out.println(directed);
        Graphs.bfs(directed, one, System.out::println);

        System.out.println("--------------------");

        // 无向图
        // 1: 2->3->4
        // 2: 1->3
        // 3: 1->2->4->5
        // 4: 1->3
        // 5: 3
        //    1
        //   /|\
        //  2-3-4
        //    |
        //    5
        edgeList = new ArrayList<>();
        edgeList.add(SimpleEdge.of(one, two));
        edgeList.add(SimpleEdge.of(one, three));
        edgeList.add(SimpleEdge.of(one, four));
        edgeList.add(SimpleEdge.of(two, three));
        edgeList.add(SimpleEdge.of(three, four));
        edgeList.add(SimpleEdge.of(three, five));
        AdjacencyList<Integer, Integer> unDirected = unDirected(vertexList, edgeList);
        System.out.println(unDirected);
        Graphs.bfs(unDirected, one, System.out::println);
    }
}
