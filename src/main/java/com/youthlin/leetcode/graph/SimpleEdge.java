package com.youthlin.leetcode.graph;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author youthlin.chen @ 2020-06-09 20:48:19
 */
@Data
@AllArgsConstructor(staticName = "of")
public class SimpleEdge<K> implements Edge<K, K> {
    SimpleVertex<K> from;
    SimpleVertex<K> to;
}
