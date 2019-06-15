package com.youthlin.leetcode.tree;

import org.junit.Before;

/**
 * @author : youthlin.chen @ 2019-06-15 16:52
 */
public class TreeTestBase {
    protected TreeNode<Integer> root;

    @Before
    public void setUp() throws Exception {
        /*
         *  1
         *    \
         *     2
         *    /
         *   3
         * */
        root = new TreeNode<>(1);
        root.right = new TreeNode<>(2);
        root.right.left = new TreeNode<>(3);
        System.out.println(root);
    }

}
