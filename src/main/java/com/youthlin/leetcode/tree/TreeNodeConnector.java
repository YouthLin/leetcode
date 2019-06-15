package com.youthlin.leetcode.tree;

import lombok.Data;

import java.util.LinkedList;
import java.util.Queue;

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

        Node(Integer val) {
            data = val;
        }

    }

    public Node connect(Node root) {
        if (root == null)
            return null;
        Queue<Node> q1 = new LinkedList<>();
        Queue<Node> q2 = new LinkedList<>();
        q1.offer(root);
        Node pre = null, node;
        do {
            while (!q1.isEmpty()) {
                node = q1.poll();
                if (pre != null) {
                    pre.next = node;
                }
                pre = node;
                if (node.left != null)
                    q2.offer(node.getLeft());
                if (node.right != null)
                    q2.offer(node.getRight());
            }
            Queue<Node> tmp = q1;
            q1 = q2;
            q2 = tmp;
            pre = null;
        } while (!q1.isEmpty());
        return root;
    }

}
