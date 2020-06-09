package com.youthlin.leetcode.graph;

import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 邻接表
 *
 * @author youthlin.chen @ 2020-06-09 20:29:51
 */
public class AdjacencyList<K, V> {
    @RequiredArgsConstructor
    private static class Node<K, V> {
        /*** 顶点 */
        final Vertex<K, V> vertex;
        Node<K, V> next;

        private static <K, V> String toString(Node<K, V> node) {
            if (node == null) {
                return "null";
            }
            StringBuilder sb = new StringBuilder();
            while (node != null) {
                sb.append(node.vertex).append("->");
                node = node.next;
            }
            return sb.toString();
        }
    }

    private static class NodeList<K, V> {
        Node<K, V> head;
        Node<K, V> tail;

        @Override
        public String toString() {
            return Node.toString(head);
        }

        void addLast(Node<K, V> node) {
            if (head == null) {
                head = tail = node;
            } else {
                tail.next = node;
                tail = node;
            }
        }
    }

    private Map<Vertex<K, V>, NodeList<K, V>> map;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<Vertex<K, V>, NodeList<K, V>> entry : map.entrySet()) {
            Vertex<K, V> vertex = entry.getKey();
            NodeList<K, V> nodeList = entry.getValue();
            sb.append("【")
                    .append(vertex)
                    .append(" => ")
                    .append(Node.toString(nodeList.head))
                    .append("】\n");
        }
        return sb.toString();
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
            adjacencyList.map.put(vertex, new NodeList<>());
        }
        for (Edge<K, V> edge : edgeList) {
            Vertex<K, V> from = edge.getFrom();
            Vertex<K, V> to = edge.getTo();
            adjacencyList.map.get(from).addLast(new Node<>(to));
            if (!directed) {
                adjacencyList.map.get(to).addLast(new Node<>(from));
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

        System.out.println("--------------------");

        // 无向图
        // 1: 2->3->4
        // 2: 1->3
        // 3: 1->2->4->5
        // 4: 1->3
        // 5: 3
        edgeList = new ArrayList<>();
        edgeList.add(SimpleEdge.of(one, two));
        edgeList.add(SimpleEdge.of(one, three));
        edgeList.add(SimpleEdge.of(one, four));
        edgeList.add(SimpleEdge.of(two, three));
        edgeList.add(SimpleEdge.of(three, four));
        edgeList.add(SimpleEdge.of(three, five));
        AdjacencyList<Integer, Integer> unDirected = unDirected(vertexList, edgeList);
        System.out.println(unDirected);
    }
}
