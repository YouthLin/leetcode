package com.youthlin.leetcode.queue;

import java.util.Comparator;
import java.util.NoSuchElementException;
import java.util.Objects;

/**
 * 先进先出队列 同时需要 O(1) 获取最大值
 *
 * @author : youthlin.chen @ 2019-08-13 21:46
 */
public class OrderList<T> {
    private static class Node<T> {
        private T data;
        private Node<T> next;
        private Node<T> smaller;
        private Node<T> bigger;

        private Node(T data) {
            this.data = data;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder("{");
            sb.append("data=").append(data);
            if (next != null) {
                sb.append(",next=").append(next.data);
            }
            if (bigger != null) {
                sb.append(",bigger=").append(bigger.data);
            }
            if (smaller != null) {
                sb.append(",smaller=").append(smaller.data);
            }
            return sb.append('}').toString();
        }
    }

    private Node<T> head;
    private Node<T> tail;
    private Node<T> max;
    private Comparator<T> comparator;

    public OrderList() {
    }

    public OrderList(Comparator<T> comparator) {
        this.comparator = comparator;
    }

    public OrderList<T> add(T data) {
        Node<T> node = new Node<>(Objects.requireNonNull(data, "Can not add null"));
        if (head == null) {
            head = max = this.tail = node;
        } else {
            tail = addAfter(tail, node);
            Node<T> bigger = findSmallestBigger(max, node);
            if (bigger == null) {
                //the add data is max
                max.bigger = node;
                node.smaller = max;
                max = node;
            } else {
                addAfterBigger(bigger, node);
            }
        }
        return this;
    }

    private static <T> Node<T> addAfter(Node<T> tail, Node<T> node) {
        tail.next = node;
        return node;
    }

    private static <T> void addAfterBigger(Node<T> bigger, Node<T> node) {
        Node<T> smaller = bigger.smaller;
        if (smaller != null) {
            smaller.bigger = node;
            node.smaller = smaller;
        }
        node.bigger = bigger;
        bigger.smaller = node;
    }

    /**
     * 返回比 node 大一点点的结点
     * 若没有比 node 大的 返回 null
     */
    private Node<T> findSmallestBigger(Node<T> max, Node<T> node) {
        if (comparator == null) {
            if (castData(max).compareTo(node.data) < 0) {
                return null;
            }
            while (true) {
                if (max.smaller == null) {
                    return max;
                }
                if (castData(max.smaller).compareTo(node.data) > 0) {
                    max = max.smaller;
                } else {
                    return max;
                }
            }
        } else {
            if (comparator.compare(max.data, node.data) < 0) {
                return null;
            }
            while (true) {
                if (max.smaller == null) {
                    return max;
                }
                if (comparator.compare(max.smaller.data, node.data) > 0) {
                    max = max.smaller;
                } else {
                    return max;
                }
            }
        }

    }

    @SuppressWarnings("unchecked")
    private static <T> Comparable<T> castData(Node<T> node) {
        return (Comparable<T>) node.data;
    }

    public T remove() {
        if (head == null) {
            throw new NoSuchElementException();
        }

        Node<T> remove = head;
        head = head.next;
        remove.next = null;

        Node<T> bigger = remove.bigger;
        Node<T> smaller = remove.smaller;
        if (bigger == null) {
            assert remove == max;
            max = max.smaller;
        } else {
            bigger.smaller = smaller;
            if (smaller != null) {
                smaller.bigger = bigger;
            }
        }
        remove.smaller = null;
        remove.bigger = null;
        return remove.data;
    }

    public T max() {
        if (max == null) {
            throw new NoSuchElementException();
        }
        return max.data;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        Node<T> n = head;
        while (n != null) {
            sb.append(n.data);
            n = n.next;
            if (n != null) {
                sb.append(", ");
            }
        }
        sb.append("] (");
        n = max;
        while (n != null) {
            sb.append(n.data);
            n = n.smaller;
            if (n != null) {
                sb.append(" > ");
            }
        }
        return sb.append(")").toString();
    }

    public static void main(String[] args) {
        OrderList<Integer> list = new OrderList<>();
        System.out.println(list);
        addAndPrint(list, 10);
        addAndPrint(list, 9);
        addAndPrint(list, 12);
        addAndPrint(list, 8);
        addAndPrint(list, 20);
        removeAndPrint(list);
        removeAndPrint(list);
        removeAndPrint(list);
        removeAndPrint(list);
        removeAndPrint(list);
        //removeAndPrint(list);
        OrderList<Node<Integer>> nodeOrderList = new OrderList<>(Comparator.comparing(n -> n.data));
        System.out.println(nodeOrderList);
        addAndPrint(nodeOrderList, new Node<>(10));
        addAndPrint(nodeOrderList, new Node<>(9));
        addAndPrint(nodeOrderList, new Node<>(12));
        addAndPrint(nodeOrderList, new Node<>(8));
        addAndPrint(nodeOrderList, new Node<>(20));
        removeAndPrint(nodeOrderList);
        removeAndPrint(nodeOrderList);
        removeAndPrint(nodeOrderList);
        removeAndPrint(nodeOrderList);
        removeAndPrint(nodeOrderList);
        removeAndPrint(nodeOrderList);
    }

    private static <T> void addAndPrint(OrderList<T> list, T data) {
        list.add(data);
        System.out.println("+ " + data + " " + list);
    }

    private static <T> void removeAndPrint(OrderList<T> list) {
        System.out.println("- " + list.remove() + " " + list);
    }
}
