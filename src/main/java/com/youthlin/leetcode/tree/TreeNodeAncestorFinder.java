package com.youthlin.leetcode.tree;

import java.util.Arrays;
import java.util.Objects;
import java.util.Stack;

/**
 * @author : youthlin.chen @ 2019-06-16 23:02
 */
public class TreeNodeAncestorFinder {
    /**
     * 所有结点的值都是唯一的。
     * p、q 为不同结点且均存在于给定的二叉树中。
     */
    public TreeNode<Integer> lowestCommonAncestor(TreeNode<Integer> root, TreeNode<Integer> p, TreeNode<Integer> q) {
        if (Objects.equals(root.getData(), p.getData())) {
            return root;
        }
        if (Objects.equals(root.getData(), q.getData())) {
            return root;
        }
        Stack<TreeNode<Integer>> l1 = new Stack<>();
        Stack<TreeNode<Integer>> l2 = new Stack<>();
        find(root, p, l1);
        find(root, q, l2);
        return findFirstSameNode(l1, l2);
    }

    private boolean find(TreeNode<Integer> node, TreeNode<Integer> target, Stack<TreeNode<Integer>> route) {
        if (node == null)
            return false;
        if (Objects.equals(node.getData(), target.getData())) {
            route.push(node);
            return true;
        }
        if (find(node.left, target, route)) {
            route.push(node);
            return true;
        }
        if (find(node.right, target, route)) {
            route.push(node);
            return true;
        }
        return false;
    }

    private TreeNode<Integer> findFirstSameNode(Stack<TreeNode<Integer>> l1, Stack<TreeNode<Integer>> l2) {
        int len1 = l1.size();
        int len2 = l2.size();
        int len = Math.min(len1, len2);
        TreeNode<Integer> pre = null;
        TreeNode<Integer> tmp;
        for (int i = 0; i < len; i++) {
            tmp = l1.pop();
            if (Objects.equals(tmp.getData(), l2.pop().getData())) {
                pre = tmp;
            } else {
                break;
            }
        }
        return pre;
    }

    public static void main(String[] args) {
        TreeNode<Integer> root = new TreeBuilder()
                .buildLevelOrder(Arrays.asList(3, 5, 1, 6, 2, 0, 8, null, null, 7, 4), TreeNode::new);
        System.out.println(root);
        TreeNodeAncestorFinder finder = new TreeNodeAncestorFinder();
        System.out.println(finder.lowestCommonAncestor(root, new TreeNode<>(5), new TreeNode<>(1)));
        System.out.println(finder.lowestCommonAncestor(root, new TreeNode<>(5), new TreeNode<>(4)));
    }
}
