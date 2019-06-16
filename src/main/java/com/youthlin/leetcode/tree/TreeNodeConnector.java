package com.youthlin.leetcode.tree;

import lombok.Data;

import java.util.function.Consumer;

/**
 * @author : youthlin.chen @ 2019-06-15 20:25
 */
public class TreeNodeConnector {
    @Data
    public static class Node implements BinTreeNode<Integer, Node> {
        Integer data;
        Node left;
        Node right;
        int offset;
        public Node next;
    }

    public Node connect(Node root) {
        Wrapper<Node> pre = new Wrapper<>();
        new TreeVisitor().levelOrder(root, node -> {
            if (pre.getData() != null) {
                pre.getData().next = node;
            }
            pre.setData(node);
        }, level -> pre.setData(null));
        return root;
    }

}
