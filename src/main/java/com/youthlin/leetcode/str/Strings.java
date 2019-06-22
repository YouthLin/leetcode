package com.youthlin.leetcode.str;

import java.util.HashSet;
import java.util.Set;

/**
 * @author : youthlin.chen @ 2019-06-17 23:03
 */
public class Strings {
    /**
     * 无重复字符的最长子串的长度
     * 枚举法
     */
    public int lengthOfLongestSubstring(String s) {
        int len = s.length();
        Set<Character> set = new HashSet<>();
        int count = 0;
        for (int i = 0; i < len && len - i > count; i++) {
            // len - i > count: 从 i 到结束的总长度 都不大于已知答案 就已经是最大 count 了
            set.clear();
            for (int j = i; j < len; j++) {
                if (set.add(s.charAt(j))) {
                    count = Math.max(count, set.size());
                } else {
                    break;
                }
            }
        }
        return count;
    }

    public static void main(String[] args) {
        Strings test = new Strings();
        System.out.println(test.lengthOfLongestSubstring("au"));
    }

    /**
     * 滑动窗口法
     */
    public int lengthOfLongestSubstringSlidingWidow(String s) {
        // TODO: 2019-06-22
        return 0;
    }

}
