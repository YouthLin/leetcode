package com.youthlin.leetcode.tree;

import lombok.Data;

/**
 * @author : youthlin.chen @ 2019-06-15 16:00
 */
@Data
public class TreeNode<T> implements BinTreeNode<T, TreeNode<T>> {
    T data;
    TreeNode<T> left;
    TreeNode<T> right;
    int offset;

    TreeNode(T val) {
        this.data = val;
    }

    @Override public String toString() {
        return TreePrinter.printTree(this);
    }

}
