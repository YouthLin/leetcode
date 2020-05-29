package com.youthlin.leetcode.tree;

import java.util.Objects;

/**
 * 前缀树
 * https://leetcode-cn.com/problems/implement-trie-prefix-tree/
 * <p>
 * 你可以假设所有的输入都是由小写字母 a-z 构成的。
 * 保证所有输入均为非空字符串。
 *
 * @author youthlin.chen @ 2020-05-29 17:35:06
 */
public class Trie {
    /*** https://leetcode-cn.com/problems/implement-trie-prefix-tree/solution/shi-xian-trie-qian-zhui-shu-by-leetcode/ */
    private static class TrieNode {
        private final int R = 26;
        /*** links[0] 表示本节点是 'a', links[25] 表示 'z' */
        private final TrieNode[] links = new TrieNode[R];
        /*** 本节点是否是某个单词的结尾 */
        private boolean isEnd;

        /*** 获取本节点中某个字母处的子节点 */
        private TrieNode get(char ch) {
            return links[ch - 'a'];
        }

        /*** 本节点是否包含某个字母 */
        private boolean has(char ch) {
            return links[ch - 'a'] != null;
        }

        /*** 本节点中某个字母处新建一个子节点 */
        private void newAt(char ch) {
            links[ch - 'a'] = new TrieNode();
        }
    }

    private final TrieNode root;

    /**
     * 01234567890123456789012345
     * abcdefghijklmnopqrstuvwxyz
     * 插入 "hello":
     * <pre>//每行是一个节点
     *        h
     *      e
     *            l
     *            l
     *               o
     * o=end
     * </pre>
     * 再插入 "world":
     * <pre>
     *        h              w
     *      e        o
     *            l     r
     *            l       t
     *               o
     * o=end, t=end
     * </pre>
     */
    public Trie() {
        root = new TrieNode();
    }

    public void insert(String word) {
        Objects.requireNonNull(word);
        TrieNode node = root;
        int length = word.length();
        for (int i = 0; i < length; i++) {
            char ch = word.charAt(i);
            assert ch >= 'a';
            assert ch <= 'z';
            if (!node.has(ch)) {
                node.newAt(ch);
            }
            node = node.get(ch);
        }
        node.isEnd = true;
    }

    public boolean search(String word) {
        Objects.requireNonNull(word);
        TrieNode end = searchPrefix(word);
        return end != null && end.isEnd;
    }

    private TrieNode searchPrefix(String word) {
        TrieNode node = root;
        int length = word.length();
        for (int i = 0; i < length; i++) {
            char ch = word.charAt(i);
            assert ch >= 'a';
            assert ch <= 'z';
            if (node.has(ch)) {
                node = node.get(ch);
            } else {
                return null;
            }
        }
        return node;
    }

    public boolean startsWith(String prefix) {
        Objects.requireNonNull(prefix);
        TrieNode end = searchPrefix(prefix);
        return end != null;
    }

}
