package com.youthlin.leetcode.str;

/**
 * @author : youthlin.chen @ 2019-09-16 21:51
 */
public class CommonPrefix {
    /**
     * 所有输入只包含小写字母 a-z 。
     */
    public static String longestCommonPrefix(String[] strs) {
        if (strs == null || strs.length == 0) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        int firstLen = strs[0].length();
        int arrLen = strs.length;
        char ch = ' ';
        for (int i = 0; i < firstLen; i++) {
            for (int j = 0; j < arrLen; j++) {
                if (strs[j].length() > i) {
                    if (ch == ' ') {
                        ch = strs[j].charAt(i);
                    } else if (ch == strs[j].charAt(i)) {
                        continue;
                    } else
                        return sb.toString();
                } else {
                    return sb.toString();
                }
            }
            sb.append(ch);
            ch = ' ';
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        System.out.println(longestCommonPrefix(new String[] { "flight", "flaa", "flbb" }));
    }
}
