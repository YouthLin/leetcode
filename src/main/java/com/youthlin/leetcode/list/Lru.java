package com.youthlin.leetcode.list;

import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;

/**
 * @author youthlin.chen @ 2020-06-07 10:37:22
 */
public class Lru<K, V> {
    /*** 额定容量 */
    private final int cap;
    /*** 背后的 Map value 是 Node 节点 */
    private final Map<K, Node<K, V>> map;
    /*** 双向链表，表头表示最近访问的，表尾表示最久没有访问的 */
    private final DoubleList<K, V> list;

    @RequiredArgsConstructor
    private static class Node<K, V> {
        final K key;
        final V value;
        Node<K, V> prev, next;

        @Override
        public String toString() {
            return "(" + key + ',' + value + ')';
        }
    }

    private static class DoubleList<K, V> {
        Node<K, V> head, tail;

        void addFirst(Node<K, V> node) {
            if (head == null) {
                // 第一个
                head = tail = node;
            } else {
                node.next = head;
                head.prev = node;
                head = node;
            }
        }

        Node<K, V> removeLast() {
            if (tail == null) {
                throw new NoSuchElementException();
            }
            Node<K, V> remove = tail;
            Node<K, V> prev = tail.prev;
            tail.prev = null;
            if (prev != null) {
                prev.next = null;
            }
            tail = prev;
            return remove;
        }

        void remove(Node<K, V> node) {
            Objects.requireNonNull(node);
            if (node == head) {
                head = node.next;
            }
            if (node == tail) {
                tail = node.prev;
            }
            Node<K, V> prev = node.prev;
            Node<K, V> next = node.next;
            if (prev != null) {
                prev.next = next;
            }
            if (next != null) {
                next.prev = prev;
            }
            node.prev = null;
            node.next = null;
        }

        @Override
        public String toString() {
            if (head == null) {
                return "null";
            }
            StringBuilder sb = new StringBuilder();
            Node<K, V> node = this.head;
            while (node != null) {
                sb.append(node).append("->");
                node = node.next;
            }
            return sb.toString();
        }
    }

    public Lru(int cap) {
        this.cap = cap;
        this.map = new HashMap<>();
        this.list = new DoubleList<>();
    }

    public void put(K key, V value) {
        Node<K, V> oldNode = map.get(key);
        if (oldNode != null) {
            // 先删除旧的节点
            list.remove(oldNode);
        } else {
            // 原来没有，数量增加了。再增加一个就超过了，所以先删一个。
            if (map.size() >= cap) {
                // 删除链表最后一个
                Node<K, V> last = list.removeLast();
                map.remove(last.key);
            }
        }
        // 最近访问的，插入到头部
        Node<K, V> node = new Node<>(key, value);
        list.addFirst(node);
        map.put(key, node);
    }

    public V get(K key) {
        Node<K, V> kvNode = map.get(key);
        if (kvNode != null) {
            // 利用 put 方法从 list 里提前
            put(kvNode.key, kvNode.value);
            return kvNode.value;
        }
        return null;
    }

    public V remove(K key) {
        Node<K, V> remove = map.remove(key);
        if (remove != null) {
            list.remove(remove);
            return remove.value;
        }
        return null;
    }

    @Override
    public String toString() {
        return "Lru{map=" + map + ", list=" + list + '}';
    }

    public static void main(String[] args) {
        Lru<Integer, String> lru = new Lru<>(3);
        lru.put(0, "a");
        lru.put(1, "b");
        lru.put(2, "c");
        lru.put(0, "a");
        System.out.println(lru.remove(0));
        System.out.println(lru);
        lru.put(3, "d");
        System.out.println(lru);

    }
}
