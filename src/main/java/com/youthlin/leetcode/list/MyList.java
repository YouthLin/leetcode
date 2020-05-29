package com.youthlin.leetcode.list;

import java.util.Objects;

/**
 * @author youthlin.chen @ 2020-05-29 16:45:56
 */
public class MyList {
    public static void main(String[] args) {
        System.out.println(removeDuplicates(Node.of()));
        System.out.println(removeDuplicates(Node.of(1)));
        System.out.println(removeDuplicates(Node.of(1, 1)));
        System.out.println(removeDuplicates(Node.of(1, 2)));
        System.out.println(removeDuplicates(Node.of(1, 2, 2)));
        System.out.println(removeDuplicates(Node.of(1, 2, 2, 3, 4)));
        System.out.println(removeDuplicates(Node.of(1, 1, 2, 2, 3, 4)));
    }

    /**
     * 单调单链表，移除重复节点
     * 如
     * 1-2-2-3-4  => 1-3-4
     * 1-1-2-3-4  => 2-3-4
     */
    public static <T> Node<T> removeDuplicates(Node<T> head) {
        if (head == null || head.getNext() == null) {
            return head;
        }
        Node<T> fakeHead = new Node<>();
        fakeHead.setNext(head);
        Node<T> prev = fakeHead, p = head;
        while (p != null && p.getNext() != null) {
            //   p
            // o-1-2-2-3-4
            // o-1-1-2-3-4
            if (!Objects.equals(p.getData(), p.getNext().getData())) {
                prev = p;
                p = p.getNext();
                //   v p
                // o-1-2-2-3-4
            } else {
                // p与之后的相等，找到末尾那个
                while (p.getNext() != null && Objects.equals(p.getData(), p.getNext().getData())) {
                    p = p.getNext();
                }
                //     p
                // o-1-1-2-3-4
                prev.setNext(p.getNext());
                p = p.getNext();
                // v     p
                // o-1-1-2-3-4
            }
        }
        return fakeHead.getNext();
    }
}
