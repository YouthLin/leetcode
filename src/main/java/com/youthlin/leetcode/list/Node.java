package com.youthlin.leetcode.list;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

/**
 * @author : youthlin.chen @ 2019-08-11 20:19
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Node<T> {
    private T data;
    private Node<T> next;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(String.valueOf(data));
        Node<T> n = next;
        while (n != null) {
            sb.append("->").append(n.data);
            n = n.next;
        }
        return sb.toString();
    }

    public static <E> Node<E> of(E... elements) {
        Objects.requireNonNull(elements);
        Node<E> fakeHead = new Node<>();
        Node<E> pre = fakeHead;
        for (E element : elements) {
            Node<E> current = new Node<>(element, null);
            pre.setNext(current);
            pre = current;
        }
        return fakeHead.next;
    }
}
