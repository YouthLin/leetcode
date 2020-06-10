package com.youthlin.leetcode.graph;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 图的顶点
 *
 * @author youthlin.chen @ 2020-06-09 20:44:21
 */
@Data
@AllArgsConstructor(staticName = "of")
public class Vertex<T> {
    T data;
}
