package com.youthlin.leetcode.list;

/**
 * @author : youthlin.chen @ 2019-08-11 20:19
 */
public class ReverseList {
    /**
     * 反转链表
     */
    public static <T> Node<T> reverse(Node<T> head) {
        if (head == null || head.getNext() == null) {
            return head;
        }
        Node<T> reversedHead = null;
        Node<T> current = head;
        Node<T> next;
        do {
            next = current.getNext();
            current.setNext(reversedHead);
            reversedHead = current;
            current = next;
        } while (current != null);
        return reversedHead;
    }

    /**
     * 从尾部开始 k 个元素为一组，反转每组。不足一组不反转
     * 如
     * 1->2 -> 3->4->5 -> 6->7->8 k=3
     * 反转为
     * 1->2 -> 5->4->3 -> 8->7->6
     */
    public static <T> Node<T> reverseByGroupFromTail(Node<T> head, int k) {
        if (head == null || k <= 1) {
            return head;
        }
        //先反转整个                  reversedHead
        //                          /
        //1<-2 <- 3<-4<-5 <- 6<-7<-8
        //
        //s=start e=end
        //         --------------->
        //        ^                |
        //e  s    e     s    e     s
        //1<-2 <- 3<-4<-5 <- 6<-7<-8
        //
        //e  s    s     e    s     e
        //1->2    5->4->3    8->7->6
        //  |
        // 不足 1 组
        //
        //1->2 -> 5->4->3 -> 8->7->6

        //记录上一组的开头
        Node<T> preStart = null;
        //每一组的开头
        Node<T> groupStart = reverse(head);
        Node<T> groupEnd, current;
        int i;
        do {
            current = groupStart;
            //每组 k 个
            for (i = 1; i < k && current.getNext() != null; i++) {
                current = current.getNext();
            }
            //更新每组结尾
            groupEnd = current;
            if (i != k) {
                //不足 1 组
                //e  s   pre
                //1<-2    5->4->3    8->7->6
                Node<T> tmp = groupStart;
                groupStart = reverse(groupStart);
                groupEnd = tmp;
                //s  e
                //1->2    5->4->3    8->7->6
                current = groupStart;
            }
            //下一组开头
            Node<T> next = current.getNext();
            //本组结尾的下一个链接到上一组开头
            groupEnd.setNext(preStart);
            //本组结束 更新 preStart 为本组开头
            preStart = groupStart;
            //下一组开头
            groupStart = next;
            //最后一组不足 k 时，groupStart 不是 next 所以还要 i==k 判断
        } while (groupStart != null && i == k);
        return preStart;
    }

    public static void main(String[] args) {
        Node<Integer> head = Node.of(1, 2, 3, 4, 5, 6, 7, 8);
        System.out.println("input          = " + head);
        System.out.println("reverse        = " + reverse(head));
        head = Node.of(1, 2, 3, 4, 5, 6, 7, 8);
        System.out.println("input          = " + head);
        System.out.println("reverseByGroup2= " + reverseByGroupFromTail(head, 2));
        head = Node.of(1, 2, 3, 4, 5, 6, 7, 8);
        System.out.println("input          = " + head);
        System.out.println("reverseByGroup3= " + reverseByGroupFromTail(head, 3));
    }

}
