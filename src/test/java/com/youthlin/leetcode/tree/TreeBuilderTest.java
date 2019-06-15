package com.youthlin.leetcode.tree;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author : youthlin.chen @ 2019-06-15 17:16
 */
public class TreeBuilderTest {
    private TreeBuilder builder = new TreeBuilder();
    private TreeVisitor visitor = new TreeVisitor();

    @Test
    public void build() {
        int[] pre = new int[] { 3, 9, 20, 15, 7 };
        int[] in = new int[] { 9, 3, 15, 20, 7 };
        int[] post = new int[] { 9, 15, 7, 20, 3 };
        TreeNode<Integer> root = builder.buildTreeViaInPostOrder(in, post);
        List<Integer> inList = new ArrayList<>();
        List<Integer> postList = new ArrayList<>();
        visitor.inOrderTraversal(root, e -> inList.add(e.getData()));
        visitor.postOrderTraversal(root, e -> postList.add(e.getData()));
        System.out.println(inList);
        System.out.println(postList);
        System.out.println(root);

        root = builder.buildTreeViaPreInOrder(pre, in);
        visitor.preOrderTraversal(root, e -> System.out.printf("%d ", e.getData()));
        System.out.println();
        visitor.inOrderTraversal(root, e -> System.out.printf("%d ", e.getData()));
        System.out.println();
        System.out.println(root);

        Integer[] level = { 3, 5, 1, 6, 2, 0, 8, null, null, 7, 4 };
        root = builder.buildViaLevelOrder(Arrays.asList(level));
        System.out.println(root);
    }

}
