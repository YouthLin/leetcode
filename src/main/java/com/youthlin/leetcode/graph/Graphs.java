package com.youthlin.leetcode.graph;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Objects;
import java.util.Queue;
import java.util.Set;
import java.util.function.Consumer;

/**
 * 图的工具类
 *
 * @author youthlin.chen @ 2020-06-09 21:42:40
 */
public class Graphs {
    /*** 广度优先 */
    public static <T> void bfs(Graph<T> graph,
            Vertex<T> start,
            Consumer<Vertex<T>> action) {
        Objects.requireNonNull(graph);
        Objects.requireNonNull(start);
        Objects.requireNonNull(action);
        Queue<Vertex<T>> queue = new LinkedList<>();
        Set<Vertex<T>> visited = new HashSet<>();
        Vertex<T> vertex = start;
        queue.add(vertex);
        while (!queue.isEmpty()) {
            vertex = queue.poll();
            if (!visited.contains(vertex)) {
                action.accept(vertex);
                visited.add(vertex);
            }
            for (Edge<T> edge : graph.edges(vertex)) {
                if (!visited.contains(edge.getTo())) {
                    queue.offer(edge.getTo());
                }
            }
        }
    }

    public static void main(String[] args) {
        testUnDirected();
        testDirected();
    }

    private static void testUnDirected() {
        System.out.println("无向图");
        AdjacencyList<Integer> graph = AdjacencyList.unDirected();
        /*
         *   1
         *  /|\
         * 2-3-4
         *   |
         *   5
         * */
        Vertex<Integer> one = Vertex.of(1);
        Vertex<Integer> two = Vertex.of(2);
        Vertex<Integer> three = Vertex.of(3);
        Vertex<Integer> four = Vertex.of(4);
        Vertex<Integer> five = Vertex.of(5);
        graph.addEdge(Edge.of(one, two));
        graph.addEdge(Edge.of(one, three));
        graph.addEdge(Edge.of(one, four));
        graph.addEdge(Edge.of(two, three));
        graph.addEdge(Edge.of(three, four));
        graph.addEdge(Edge.of(three, five));
        System.out.println(graph);
        // 1 开始：[1],[2,3,4],[5]
        Graphs.bfs(graph, one, System.out::println);
        System.out.println("---");
        // 2 开始：[2],[1,3],[4,5]
        Graphs.bfs(graph, two, System.out::println);
        System.out.println("---");
        // 3 开始：[3],[1,2,4,5]
        Graphs.bfs(graph, three, System.out::println);

        graph.removeEdge(Edge.of(three, five));
        // 移除 3-5
        System.out.println("remove (3,5)");
        System.out.println(graph);
    }

    private static void testDirected() {
        System.out.println("有向图：");
        /* 点代表箭头
         *     1
         *    /.\
         *   / | \
         *  /  |  \
         * .   .   .
         * 2--.3.-- 4
         *     |
         *     .
         *     5
         * */
        AdjacencyList<Integer> graph = AdjacencyList.directed();
        Vertex<Integer> one = Vertex.of(1);
        Vertex<Integer> two = Vertex.of(2);
        Vertex<Integer> three = Vertex.of(3);
        Vertex<Integer> four = Vertex.of(4);
        Vertex<Integer> five = Vertex.of(5);
        graph.addEdge(Edge.of(one, two));
        graph.addEdge(Edge.of(one, three));
        graph.addEdge(Edge.of(one, four));
        graph.addEdge(Edge.of(two, three));
        graph.addEdge(Edge.of(three, one));
        graph.addEdge(Edge.of(three, five));
        graph.addEdge(Edge.of(four, three));
        System.out.println(graph);
        // 1 开始：[1],[2,3,4],[5]
        Graphs.bfs(graph, one, System.out::println);
        System.out.println("---");
        // 2 开始：[2],[3],[1,5],[4]
        Graphs.bfs(graph, two, System.out::println);
        System.out.println("---");
        // 3 开始：[3],[1,5],[2,4]
        Graphs.bfs(graph, three, System.out::println);

        graph.removeEdge(Edge.of(two, three));
        System.out.println("remove <2-3>:");
        System.out.println(graph);
        // 2 开始：[2]
        Graphs.bfs(graph, two, System.out::println);

    }
}
