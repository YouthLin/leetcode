package com.youthlin.leetcode.tree;

import org.junit.Test;

/**
 * @author : youthlin.chen @ 2019-06-15 16:10
 */
public class TreeVisitorTest extends TreeTestBase {
    private TreeVisitor visitor = new TreeVisitor();

    @Test
    public void preOrder() {
        visitor.preOrderTraversalRecursion(root, node -> System.out.printf("%d ", node.getData()));
        System.out.println();
        visitor.preOrderTraversal(root, node -> System.out.printf("%d ", node.getData()));
        System.out.println();
    }

    @Test
    public void inOrder() {
        visitor.inOrderTraversalRecursion(root, node -> System.out.printf("%d ", node.getData()));
        System.out.println();
        visitor.inOrderTraversal(root, node -> System.out.printf("%d ", node.getData()));
        System.out.println();
    }

    @Test
    public void postOrder() {
        visitor.postOrderTraversalRecursion(root, node -> System.out.printf("%d ", node.getData()));
        System.out.println();
        visitor.postOrderTraversal(root, node -> System.out.printf("%d ", node.getData()));
        System.out.println();
    }

    @Test
    public void levelOrder() {
        visitor.levelOrder(root,
                node -> System.out.printf("%d ", node.data),
                level -> System.out.println("(" + level + ")")
        );
        System.out.println();
    }

}
