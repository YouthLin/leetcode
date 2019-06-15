package com.youthlin.leetcode.tree;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Function;

/**
 * @author : youthlin.chen @ 2019-06-15 17:09
 */
public class TreeBuilder {
    private static class IntWrap {
        int value;
    }

    public TreeNode<Integer> buildTreeViaInPostOrder(int[] inOrder, int[] postOrder) {
        int len = inOrder.length;
        IntWrap postEnd = new IntWrap();
        postEnd.value = len;
        return buildNode(inOrder, 0, len, postOrder, postEnd);
    }

    private int index(int[] in, int target) {
        int len = in.length;
        for (int i = 0; i < len; i++) {
            if (in[i] == target) {
                return i;
            }
        }
        return -1;
    }

    private TreeNode<Integer> buildNode(int[] inorder, int beg, int end, int[] postorder, IntWrap postEnd) {
        //找根节点
        if (postEnd.value <= 0 || beg >= end)
            return null;
        int nodeIndex = index(inorder, postorder[postEnd.value - 1]);
        if (nodeIndex < beg || nodeIndex >= end)
            return null;
        TreeNode<Integer> node = new TreeNode<>(inorder[nodeIndex]);
        postEnd.value--;
        //
        TreeNode right = buildNode(inorder, nodeIndex + 1, end, postorder, postEnd);
        TreeNode left = buildNode(inorder, beg, nodeIndex, postorder, postEnd);
        node.right = right;
        node.left = left;
        return node;
    }

    public TreeNode<Integer> buildTreeViaPreInOrder(int[] preOrder, int[] inOrder) {
        return buildNode(preOrder, inOrder, 0, inOrder.length, new IntWrap());
    }

    private TreeNode<Integer> buildNode(int[] preorder, int[] inorder, int beg, int end, IntWrap preIndex) {
        if (preIndex.value >= preorder.length)
            return null;
        int val = preorder[preIndex.value];
        int nodeIndex = index(inorder, val);
        if (nodeIndex < beg || nodeIndex >= end)
            return null;
        TreeNode<Integer> node = new TreeNode<>(val);
        preIndex.value++;
        node.left = buildNode(preorder, inorder, beg, nodeIndex, preIndex);
        node.right = buildNode(preorder, inorder, nodeIndex + 1, end, preIndex);
        return node;
    }

    /**
     * 输入是按层次遍历的顺序 空结点用 null 表示(所以结点的 data 值永远不能为 null)
     */
    public <T> TreeNode<T> buildViaLevelOrder(Iterable<T> input) {
        return buildViaLevelOrder(input, TreeNode::new);
    }

    /**
     * 输入是按层次遍历的顺序 空结点用 null 表示(所以结点的 data 值永远不能为 null)
     */
    public <T, N extends BinTreeNode<T, N>> N buildViaLevelOrder(Iterable<T> input, Function<T, N> creator) {
        Iterator<T> iterator = input.iterator();
        if (!iterator.hasNext()) {
            return null;
        }
        List<N> list = new ArrayList<>();
        int index = 0;
        int parentIndex;
        N parent;
        while (iterator.hasNext()) {
            T next = iterator.next();
            N node = next == null ? null : creator.apply(next);
            list.add(node);
            parentIndex = (index - 1) / 2;
            if (parentIndex >= 0) {
                parent = list.get(parentIndex);
                if (index % 2 == 0) {
                    parent.setRight(node);
                } else {
                    parent.setLeft(node);
                }
            }
            index++;

        }
        return list.get(0);
    }

}
