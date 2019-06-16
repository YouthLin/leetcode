package com.youthlin.leetcode.tree;

import org.junit.Test;

import java.util.Arrays;

/**
 * @author : youthlin.chen @ 2019-06-15 23:04
 */
public class TreeNodeConnectorTest {

    @Test
    public void connect() {
        TreeBuilder builder = new TreeBuilder();
        TreeNodeConnector.Node root = builder
                .buildViaFullLevelOrder(Arrays.asList(1, 2, 3, 4, 5, null, 7, null, null, 6000, 9), TreeNodeConnector.Node::new);
        System.out.println(TreePrinter.printTree(root));

        TreeNodeConnector connector = new TreeNodeConnector();
        connector.connect(root);
        TreeVisitor visitor = new TreeVisitor();
        visitor.levelOrder(root,
                n -> {
                    TreeNodeConnector.Node next = n.getNext();
                    System.out.print(n.getData() + "->" + (next == null ? " " : next.getData() + " "));
                },
                level -> System.out.println()
        );
    }

}
