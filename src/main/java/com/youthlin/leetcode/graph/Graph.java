package com.youthlin.leetcode.graph;

/**
 * 图
 *
 * @author youthlin.chen @ 2020-06-09 22:13:22
 */
public interface Graph<T> {
    /*** 顶点个数 */
    int vertexSize();

    /*** 边的条数 */
    int edgeSize();

    /*** 添加一个顶点 */
    boolean addVertex(Vertex<T> vertex);

    /*** 添加一条边 */
    boolean addEdge(Edge<T> edge);

    /*** 移除一条边 */
    boolean removeEdge(Edge<T> edge);

    /*** 返回指定顶点出发的所有边 */
    Iterable<Edge<T>> edges(Vertex<T> vertex);

    /*** 返回所有边 */
    Iterable<Edge<T>> edges();

}
