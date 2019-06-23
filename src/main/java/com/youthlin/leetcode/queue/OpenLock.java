package com.youthlin.leetcode.queue;

import com.youthlin.leetcode.util.DebugUtil;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 你有一个带有四个圆形拨轮的转盘锁。每个拨轮都有10个数字： '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' 。每个拨轮可以自由旋转：例如把 '9' 变为  '0'，'0' 变为 '9' 。每次旋转都只能旋转一个拨轮的一位数字。
 * <p>
 * 锁的初始数字为 '0000' ，一个代表四个拨轮的数字的字符串。
 * <p>
 * 列表 deadends 包含了一组死亡数字，一旦拨轮的数字和列表里的任何一个元素相同，这个锁将会被永久锁定，无法再被旋转。
 * <p>
 * 字符串 target 代表可以解锁的数字，你需要给出最小的旋转次数，如果无论如何不能解锁，返回 -1。
 * <p>
 * <p>
 * 示例 1:
 * <p>
 * 输入：deadends = ["0201","0101","0102","1212","2002"], target = "0202"
 * 输出：6
 * 解释：
 * 可能的移动序列为 "0000" -> "1000" -> "1100" -> "1200" -> "1201" -> "1202" -> "0202"。
 * 注意 "0000" -> "0001" -> "0002" -> "0102" -> "0202" 这样的序列是不能解锁的，
 * 因为当拨动到 "0102" 时这个锁就会被锁定。
 * 示例 2:
 * <p>
 * 输入: deadends = ["8888"], target = "0009"
 * 输出：1
 * 解释：
 * 把最后一位反向旋转一次即可 "0000" -> "0009"。
 * 示例 3:
 * <p>
 * 输入: deadends = ["8887","8889","8878","8898","8788","8988","7888","9888"], target = "8888"
 * 输出：-1
 * 解释：
 * 无法旋转到目标数字且不被锁定。
 * 示例 4:
 * <p>
 * 输入: deadends = ["0000"], target = "8888"
 * 输出：-1
 * <p>
 * <p>
 * 提示：
 * <p>
 * 死亡列表 deadends 的长度范围为 [1, 500]。
 * 目标数字 target 不会在 deadends 之中。
 * 每个 deadends 和 target 中的字符串的数字会在 10,000 个可能的情况 '0000' 到 '9999' 中产生。
 *
 * @author : youthlin.chen @ 2019-06-23 20:19
 */
public class OpenLock {

    public int openLock(String[] deadends, String target) {
        Queue<Integer> queue = new LinkedList<>();
        Set<Integer> dead = Arrays.stream(deadends).map(Integer::parseInt).collect(Collectors.toSet());
        if (dead.contains(0)) {
            return -1;
        }
        int targetNum = Integer.parseInt(target);
        if (targetNum == 0) {
            return 0;
        }
        queue.offer(0);
        int value, step;
        while (!queue.isEmpty()) {
            Integer pair = queue.poll();
            value = pair % 10000;
            step = pair / 10000;
            Set<Integer> nextSeqs = nextSeqs(value);
            for (Integer next : nextSeqs) {
                if (next == targetNum) {
                    DebugUtil.printf("前一位是：%04d\n", value);
                    return step + 1;
                }
                if (!dead.contains(next)) {
                    queue.offer((step + 1) * 10000 + next);
                    dead.add(next);
                }
            }
        }
        return -1;
    }

    private static Set<Integer> nextSeqs(Integer input) {
        assert input >= 0;
        assert input <= 9999;
        Set<Integer> set = new HashSet<>();
        // 1234 input
        // i=0 base=   1 high= 123  numOfi=4  low=  0  add:1235 1233
        // i=1 base=  10 high= 12   numOfi=3  low=  4  add:1244 1224
        // i=2 base= 100 high= 1    numOfi=2  low= 34  add:1334 1134
        // i=3 base=1000 high=0     numOfi=1  low=234  add:2234 0234
        int base;
        int numOfi;
        int high, low;
        for (int i = 0; i < 4; i++) {
            base = 1;
            for (int j = i; j > 0; j--) {
                base = base * 10;
            }
            high = input / (base * 10);
            low = input % base;
            numOfi = (input / base) % 10;
            set.add(high * (base * 10) + (add(numOfi)) * base + low);
            set.add(high * (base * 10) + ((sub(numOfi)) % 10) * base + low);
        }
        return set;
    }

    private static int add(int i) {
        if (i == 9) {
            return 0;
        }
        return i + 1;
    }

    private static int sub(int i) {
        if (i == 0) {
            return 9;
        }
        return i - 1;
    }

    public static void main(String[] args) {
        OpenLock test = new OpenLock();
        System.out.println(test.openLock(new String[] { "0201", "0101", "0102", "1212", "2002" }, "0202"));
        System.out.println(test.openLock(new String[] { "0201", "0101", "0102", "1212", "2002" }, "0203"));
        System.out.println(test.openLock(new String[] { "0201", "0101", "0102", "1212", "2002" }, "0103"));
        System.out.println(test.openLock(new String[] { "0201", "0101", "0102", "1212", "2002" }, "0003"));
        System.out.println(test.openLock(new String[] { "0201", "0101", "0102", "1212", "2002" }, "0002"));
        System.out.println(test.openLock(new String[] { "0201", "0101", "0102", "1212", "2002" }, "0001"));
        System.out.println(test.openLock(new String[] { "0201", "0101", "0102", "1212", "2002" }, "0000"));
    }

    private static void printInts(Collection<Integer> set) {
        set.forEach(e -> DebugUtil.printf("%04d,", e));
        DebugUtil.println("");
    }

}
