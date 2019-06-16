package com.youthlin.leetcode.tree;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 递归可以解决的二叉树相关问题
 * - 树的最大深度
 * - 判断是否对称二叉树
 * - 判断是否存在路径总和等于给定值
 *
 * @author : youthlin.chen @ 2019-06-15 16:37
 */
public class TreeRecursion {
    //region 最大深度

    /**
     * 返回树的最大深度
     */
    public <T> int maxDepth(TreeNode<T> root) {
        return depth(root, 0);
    }

    private <T> int depth(TreeNode<T> node, int currentDepth) {
        if (node == null) {
            return currentDepth;
        }
        int left = depth(node.left, currentDepth + 1);
        int right = depth(node.right, currentDepth + 1);
        return left > right ? left : right;
    }
    //endregion 最大深度

    //region 对称判断

    /**
     * 判断树是否对称 递归判断
     */
    public <T> boolean isSymmetric(TreeNode<T> root) {
        return check(root, root);
    }

    private <T> boolean check(TreeNode<T> left, TreeNode<T> right) {
        //输入结点本身
        if (left == null && right == null) {
            return true;
        }
        if (left != null && right != null) {
        } else {
            return false;
        }

        //输入结点 1 的左子树 和输入结点 2 的右子树
        if (left.left != null && right.right != null) {
            if (!Objects.equals(left.left.data, right.right.data)) {
                return false;
            }
        } else if ((left.left == null && right.right == null)) {
        } else {
            return false;
        }

        //输入结点 2 的左子树 和输入结点 1 的右子树
        if (left.right != null && right.left != null) {
            if (!Objects.equals(left.right.data, right.left.data)) {
                return false;
            }
        } else if (left.right == null && right.left == null) {
        } else {
            return false;
        }

        return check(left.left, right.right) && check(left.right, right.left);
    }

    /**
     * 判断树是否对称 层次遍历判断
     */
    public <T> boolean isSymmetricByLevel(TreeNode<T> root) {
        return check2(root);
    }

    private <T> boolean check2(TreeNode<T> root) {
        if (root == null) {
            return true;
        }
        List<T> level = new ArrayList<>();
        return new TreeVisitor().levelOrder(root, true,
                node -> level.add(node != null ? node.data : null),
                levelCount -> {
                    boolean ok = checkLevel(level);
                    level.clear();
                    return ok;
                }
        );
    }

    /**
     * 每一层判断是否对称
     */
    private <T> boolean checkLevel(List<T> level) {
        int size = level.size();
        if (size != 1 && size % 2 != 0) {
            return false;
        }
        int mid = size >> 1;
        for (int i = 0; i < mid; i++) {
            if (!Objects.equals(level.get(i), level.get(size - i - 1))) {
                return false;
            }

        }
        return true;
    }
    //endregion 对称判断

    //region 路径总和

    public boolean hasPathSum(TreeNode<Integer> root, int sum) {
        return checkHasPathSum(root, 0, sum);
    }

    private boolean checkHasPathSum(TreeNode<Integer> node, int current, int target) {
        if (node == null) {
            return false;
        }
        if (node.left == node.right && node.left == null) {
            return current + node.data == target;
        }
        return checkHasPathSum(node.left, current + node.data, target)
                || checkHasPathSum(node.right, current + node.data, target);
    }
    //endregion 路径总和

}
