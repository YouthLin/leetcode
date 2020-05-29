package com.youthlin.leetcode.str;

import java.math.BigInteger;
import java.util.HashSet;
import java.util.Scanner;
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
        System.out.println(isPalindrome1("A man, a plan, a canal: Panama"));
        System.out.println(isPalindrome1("race a car"));
        System.out.println(isPalindrome1("."));

        test(Integer.MAX_VALUE + "");
        test(Integer.MIN_VALUE + "");
        test("2147483648");
        test(Integer.MAX_VALUE + "0");

        Strings test = new Strings();
        System.out.println(test.lengthOfLongestSubstring("au"));
        Scanner in = new Scanner(System.in);
        String a, b;
        while (in.hasNext()) {
            a = in.nextLine().trim();
            b = in.nextLine().trim();
            System.out.println(new BigInteger(a).add(new BigInteger(b)));
            System.out.println(add(a, b));

        }
    }

    /**
     * 滑动窗口法
     */
    public int lengthOfLongestSubstringSlidingWidow(String s) {
        // TODO: 2019-06-22
        return 0;
    }

    private static String add(String a, String b) {
        int aLen = a.length();
        int bLen = b.length();
        int minLen = Math.min(aLen, bLen);
        StringBuilder sb = new StringBuilder(Math.max(aLen, bLen) + 1);
        int sum = 0, left, right;
        for (int i = 1; i <= minLen; i++) {
            left = a.charAt(aLen - i) - '0';
            right = b.charAt(bLen - i) - '0';
            sum += (left + right);
            //out(sum);
            if (sum >= 10) {
                sb.append(sum % 10);
                sum = sum / 10;
            } else {
                sb.append(sum);
                sum = 0;
            }
        }
        // 1234
        //12347
        //13581
        for (int i = minLen + 1; i <= aLen; i++) {
            left = a.charAt(aLen - i);
            sum += left - '0';
            if (sum >= 10) {
                sb.append(sum % 10);
                sum = sum / 10;
            } else {
                sb.append(sum);
                sum = 0;
            }
        }
        for (int i = minLen + 1; i <= bLen; i++) {
            right = b.charAt(bLen - i);
            sum += right - '0';
            if (sum >= 10) {
                sb.append((sum % 10));
                sum = sum / 10;
            } else {
                sb.append(sum);
                sum = 0;
            }
        }
        if (sum > 0) {
            sb.append(sum);
        }
        return sb.reverse().toString();
    }

    private static void test(String input) {
        try {
            System.out.println(str2Int(input));
        } catch (Exception e) {
            System.err.println("NaN:" + input);
        }
    }

    private static int str2Int(String num) {
        if (num == null || num.trim().length() == 0) {
            throw new IllegalArgumentException();
        }
        long result = 0;
        boolean neg = false;
        if (num.startsWith("-")) {
            neg = true;
            num = num.substring(1);
        } else if (num.startsWith("+")) {
            num = num.substring(1);
        }

        int number;
        for (int i = 0, length = num.length(); i < length; i++) {
            number = num.charAt(i) - '0';
            if (number < 0 || number > 9) {
                throw new IllegalArgumentException();
            }
            result *= 10;
            result += number;

            if (!neg && result > Integer.MAX_VALUE) {
                throw new IllegalArgumentException();
            }
            if (neg && result - 1 > Integer.MAX_VALUE) {
                throw new IllegalArgumentException();
            }
        }
        if (neg) {
            return (int) -result;
        }
        return (int) result;
    }
// 8L 5L 3
    /*
     * 8 0 0
     * 5 0 3
     * 5 3 0
     * 2 3 3
     * 2 5 1
     * 7 0 1
     * 7 1 0
     * 4 1 3
     * */

    /**
     * 给定一个字符串，验证它是否是回文串，只考虑字母和数字字符，可以忽略字母的大小写。
     * <p>
     * 说明：本题中，我们将空字符串定义为有效的回文串。
     */
    public static boolean isPalindrome(String s) {
        if (s == null || s.trim().length() == 0) {
            return false;
        }
        s = s.toLowerCase();
        int len = s.length();
        StringBuilder sb = new StringBuilder(len);
        char ch;
        for (int i = 0; i < len; i++) {
            if (isAlphaOrDigit(ch = s.charAt(i))) {
                sb.append(ch);
            }
        }
        String s1 = sb.toString();
        return s1.equals(sb.reverse().toString());
    }

    public static boolean isPalindrome1(String s) {
        if (s == null || s.trim().length() == 0) return true;
        int len = s.length();
        s = s.toLowerCase();
        int i = 0, j = len - 1;
        char left, right;
        // .
        // .,
        // aba
        while (i < j) {
            left = s.charAt(i);
            if (!isAlphaOrDigit(left)) {
                i++;
                continue;
            }
            right = s.charAt(j);
            if (!isAlphaOrDigit(right)) {
                j--;
                continue;
            }
            if (left != right) {
                return false;
            }
            i++;
            j--;
        }
        return true;
    }

    private static boolean isAlphaOrDigit(char ch) {
        return (ch >= 'a' && ch <= 'z') || (ch >= 'A' && ch <= 'Z') || (ch >= '0' && ch <= '9');
    }
}
