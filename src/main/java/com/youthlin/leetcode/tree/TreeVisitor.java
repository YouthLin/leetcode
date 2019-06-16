package com.youthlin.leetcode.tree;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;
import java.util.function.Consumer;
import java.util.function.Predicate;

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
        levelFullOrder(root, false, action, levelCount -> {
            onLevelEnd.accept(levelCount);
            return true;
        });
    }

    /**
     * @param root                 树的根结点
     * @param visitNullNode        是否访问 null 结点
     * @param action               访问每个结点
     * @param shouldVisitNextLevel 是否继续访问下一层
     * @return true 如果完成了所有结点的访问
     */
    public <T, N extends BinTreeNode<T, N>> boolean levelFullOrder(N root, boolean visitNullNode,
            Consumer<N> action, Predicate<Integer> shouldVisitNextLevel) {
        if (root == null) {
            return false;
        }
        Queue<N> q1 = new LinkedList<>();
        Queue<N> q2 = new LinkedList<>();
        q1.offer(root);
        int level = 0;
        do {
            while (!q1.isEmpty()) {
                root = q1.poll();
                action.accept(root);
                if (root != null) {
                    if (root.getLeft() != null || visitNullNode) {
                        q2.offer(root.getLeft());
                    }
                    if (root.getRight() != null || visitNullNode) {
                        q2.offer(root.getRight());
                    }
                }
            }
            if (!shouldVisitNextLevel.test(level++)) {
                return false;
            }
            Queue<N> tmp = q1;
            q1 = q2;
            q2 = tmp;
        } while (!q1.isEmpty());
        return true;
    }

}
