package com.youthlin.leetcode.tree;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * @author : youthlin.chen @ 2019-06-15 17:09
 */
public class TreeBuilder {
    public <T> TreeNode<T> buildTreeViaInPostOrder(T[] inOrder, T[] postOrder) {
        int len = inOrder.length;
        Wrapper<Integer> postEnd = new Wrapper<>();
        postEnd.setData(len);
        return buildNode(inOrder, 0, len, postOrder, postEnd);
    }

    public <T> TreeNode<T> buildTreeViaPreInOrder(T[] preOrder, T[] inOrder) {
        Wrapper<Integer> postIndex = new Wrapper<>();
        postIndex.setData(0);
        return buildNode(preOrder, inOrder, 0, inOrder.length, postIndex);
    }

    /**
     * 输入是按层次遍历的顺序 空结点用 null 表示(所以结点的 data 值永远不能为 null)
     *
     * @param <T> 结点值类型
     */
    public <T> TreeNode<T> buildViaLevelOrder(Iterable<T> input) {
        return buildViaLevelOrder(input, TreeNode::new);
    }

    /**
     * 输入是按层次遍历的顺序 空结点用 null 表示(所以结点的 data 值永远不能为 null)
     *
     * @param creator 通常可以传入结点类型 N 的默认构造函数
     * @param <T>     结点值类型
     * @param <N>     结点类型
     */
    public <T, N extends BinTreeNode<T, N>> N buildViaLevelOrder(Iterable<T> input, Supplier<N> creator) {
        return buildViaLevelOrder(input, t -> {
            N n = creator.get();
            n.setData(t);
            return n;
        });
    }

    /**
     * 输入是按层次遍历的顺序 空结点用 null 表示(所以结点的 data 值永远不能为 null)
     *
     * @param creator 由结点值 T 构造 结点 N. 通常可以传入结点类型 N 的带参数 data 的构造函数
     * @param <T>     结点值类型
     * @param <N>     结点类型
     */
    public <T, N extends BinTreeNode<T, N>> N buildViaLevelOrder(Iterable<T> input, Function<T, N> creator) {
        Iterator<T> iterator = input.iterator();
        if (!iterator.hasNext()) {
            return null;
        }
        List<N> list = new ArrayList<>();
        int index = 0;
        int parentIndex;
        N parent, node;
        while (iterator.hasNext()) {
            T next = iterator.next();
            if (next == null) {
                node = null;
            } else {
                node = creator.apply(next);
            }
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

    private <T> int indexOf(T[] in, T target) {
        int len = in.length;
        for (int i = 0; i < len; i++) {
            if (target.equals(in[i])) {
                return i;
            }
        }
        return -1;
    }

    private <T> TreeNode<T> buildNode(T[] inOrder, int beg, int end, T[] postOrder, Wrapper<Integer> postEnd) {
        //找根结点
        if (postEnd.getData() <= 0 || beg >= end) {
            return null;
        }
        int nodeIndex = indexOf(inOrder, postOrder[postEnd.getData() - 1]);
        if (nodeIndex < beg || nodeIndex >= end) {
            return null;
        }
        TreeNode<T> node = new TreeNode<>(inOrder[nodeIndex]);
        postEnd.setData(postEnd.getData() - 1);
        //
        TreeNode<T> right = buildNode(inOrder, nodeIndex + 1, end, postOrder, postEnd);
        TreeNode<T> left = buildNode(inOrder, beg, nodeIndex, postOrder, postEnd);
        node.right = right;
        node.left = left;
        return node;
    }

    private <T> TreeNode<T> buildNode(T[] preOrder, T[] inOrder, int beg, int end, Wrapper<Integer> preIndex) {
        if (preIndex.getData() >= preOrder.length) {
            return null;
        }
        T val = preOrder[preIndex.getData()];
        int nodeIndex = indexOf(inOrder, val);
        if (nodeIndex < beg || nodeIndex >= end) {
            return null;
        }
        TreeNode<T> node = new TreeNode<>(val);
        preIndex.setData(preIndex.getData() + 1);
        node.left = buildNode(preOrder, inOrder, beg, nodeIndex, preIndex);
        node.right = buildNode(preOrder, inOrder, nodeIndex + 1, end, preIndex);
        return node;
    }

}
