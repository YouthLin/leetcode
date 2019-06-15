package com.youthlin.leetcode.tree;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;
import java.util.function.Consumer;

/**
 * @author : youthlin.chen @ 2019-06-15 16:02
 */
public class TreeVisitor {
    //region pre order
    public <T, N extends BinTreeNode<T, N>> void preOrderTraversalRecursion(BinTreeNode<T, N> root,
            Consumer<BinTreeNode<T, N>> action) {
        if (root == null) {
            return;
        }
        action.accept(root);
        preOrderTraversalRecursion(root.getLeft(), action);
        preOrderTraversalRecursion(root.getRight(), action);
    }

    public <T, N extends BinTreeNode<T, N>> void preOrderTraversal(BinTreeNode<T, N> root,
            Consumer<BinTreeNode<T, N>> action) {
        Stack<BinTreeNode<T, N>> stack = new Stack<>();
        do {
            while (root != null) {
                action.accept(root);
                stack.push(root);
                root = root.getLeft();
            }
            if (!stack.empty()) {
                root = stack.pop().getRight();
            }
        } while (root != null || !stack.isEmpty());
    }
    //endregion pre order

    //region in order
    public <T, N extends BinTreeNode<T, N>> void inOrderTraversalRecursion(BinTreeNode<T, N> root,
            Consumer<BinTreeNode<T, N>> action) {
        if (root == null) {
            return;
        }
        inOrderTraversalRecursion(root.getLeft(), action);
        action.accept(root);
        inOrderTraversalRecursion(root.getRight(), action);
    }

    public <T, N extends BinTreeNode<T, N>> void inOrderTraversal(BinTreeNode<T, N> root,
            Consumer<BinTreeNode<T, N>> action) {
        Stack<BinTreeNode<T, N>> stack = new Stack<>();
        do {
            while (root != null) {
                stack.push(root);
                root = root.getLeft();
            }
            if (!stack.empty()) {
                root = stack.pop();
                action.accept(root);
                root = root.getRight();
            }
        } while (root != null || !stack.isEmpty());
    }

    //endregion in order

    //region post order
    public <T, N extends BinTreeNode<T, N>> void postOrderTraversalRecursion(BinTreeNode<T, N> root,
            Consumer<BinTreeNode<T, N>> action) {
        if (root == null) {
            return;
        }
        postOrderTraversalRecursion(root.getLeft(), action);
        postOrderTraversalRecursion(root.getRight(), action);
        action.accept(root);
    }

    public <T, N extends BinTreeNode<T, N>> void postOrderTraversal(BinTreeNode<T, N> root,
            Consumer<BinTreeNode<T, N>> action) {
        Stack<BinTreeNode<T, N>> stack = new Stack<>();
        BinTreeNode<T, N> pre = root;
        while (true) {
            while (root != null) {
                stack.push(root);
                root = root.getLeft();
            }
            if (stack.empty()) {
                break;
            }
            root = stack.peek();
            if (root.getRight() != null && root.getRight() != pre) {
                root = root.getRight();
            } else {
                root = stack.pop();
                action.accept(root);
                pre = root;
                root = null;
            }
        }
    }
    //endregion post order

    /**
     * @param onLevelEnd 当每层结束时调用 参数是从 0 编号的层数
     */
    public <T, N extends BinTreeNode<T, N>> void levelOrder(N root, Consumer<N> action, Consumer<Integer> onLevelEnd) {
        if (root == null) {
            return;
        }
        Queue<N> q1 = new LinkedList<>();
        Queue<N> q2 = new LinkedList<>();
        q1.offer(root);
        int level = 0;
        do {
            while (!q1.isEmpty()) {
                root = q1.poll();
                action.accept(root);
                if (root.getLeft() != null) {
                    q2.offer(root.getLeft());
                }
                if (root.getRight() != null) {
                    q2.offer(root.getRight());
                }
            }
            onLevelEnd.accept(level++);
            Queue<N> q = q1;
            q1 = q2;
            q2 = q;
        } while (!q1.isEmpty() || !q2.isEmpty());
    }

}
