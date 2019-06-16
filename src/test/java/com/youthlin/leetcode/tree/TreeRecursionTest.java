package com.youthlin.leetcode.tree;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

/**
 * @author : youthlin.chen @ 2019-06-15 16:52
 */
public class TreeRecursionTest extends TreeTestBase {
    private TreeRecursion test = new TreeRecursion();
    private TreeNode<Integer> symmetric;

    @Before
    public void setUp() throws Exception {
        /*
         *      5
         *     / \
         *    4   8
         *   /   /\
         *  11  13  4
         *  /\       \
         * 7  2       1
         * */
        TreeNode<Integer> left = new TreeNode<>(7);
        TreeNode<Integer> right = new TreeNode<>(2);
        root = new TreeNode<>(11);
        root.left = left;
        root.right = right;
        left = root;
        root = new TreeNode<>(4);
        root.left = left;
        left = root;
        root = new TreeNode<>(5);
        root.left = left;
        right = new TreeNode<>(8);
        root.right = right;
        right.left = new TreeNode<>(13);
        right.right = new TreeNode<>(4);
        right = right.right;
        right.right = new TreeNode<>(1);
        System.out.println(root);
        /*
         *     1
         *    / \
         *   2   2
         *  /\   /\
         * 3  4 4  3
         * */
        symmetric = new TreeNode<>(1);
        symmetric.left = new TreeNode<>(2);
        symmetric.right = new TreeNode<>(2);
        left = symmetric.left;
        right = symmetric.right;
        left.left = new TreeNode<>(3);
        left.right = new TreeNode<>(4);
        right.left = new TreeNode<>(4);
        right.right = new TreeNode<>(3);
        System.out.println(symmetric);
    }

    @Test
    public void maxDepth() {
        System.out.println(test.maxDepth(root));
        Assert.assertEquals(4, test.maxDepth(root));
        Assert.assertEquals(3, test.maxDepth(symmetric));
    }

    @Test
    public void isSymmetric() {
        Assert.assertFalse(test.isSymmetric(root));
        Assert.assertTrue(test.isSymmetric(symmetric));
    }

    @Test
    public void isSymmetricByLevel() {
        Assert.assertFalse(test.isSymmetricByLevel(root));
        Assert.assertTrue(test.isSymmetricByLevel(symmetric));
    }

    @Test
    public void hasPathSum() {
        System.out.println(test.hasPathSum(root, 22));
        Integer[] level = {3, 5, 1, 6, 2, 0, 8, null, null, 7, 4};
        root = new TreeBuilder().buildViaLevelOrder(Arrays.asList(level));
        Assert.assertTrue(test.hasPathSum(root, 17));
        Assert.assertTrue(test.hasPathSum(root, 14));
    }
}