package com.youthlin.leetcode.util;

/**
 * @author : youthlin.chen @ 2019-06-22 19:11
 */
public class DebugUtil {
    private static boolean out = true;

    public static void printf(String format, Object... args) {
        if (out) {
            System.out.printf(format, args);
        }
    }

    public static void println(Object arg) {
        if (out) {
            System.out.println(arg);
        }
    }

}
