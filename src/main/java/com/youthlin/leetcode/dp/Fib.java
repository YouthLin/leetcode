package com.youthlin.leetcode.dp;

/**
 * @author youthlin.chen @ 2020-06-27 16:06:34
 */
public class Fib {
    public static void main(String[] args) {
        System.out.println(fib0(0));
        System.out.println(fib(1));
        System.out.println(fib(2));
        System.out.println(fib(3));
        System.out.println(fib(4));
        System.out.println(fib0(5));
    }

    /*** fib[0]=0 fib[1]=1 fib[2]=1 fib[n]=fib[n-1]+fib[n-2] */
    private static int fib(int n) {
        assert n >= 0;
        if (n == 0) {
            return 0;
        }
        if (n == 1 || n == 2) {
            return 1;
        }
        int[] dp = new int[n + 1];
        dp[0] = 0;
        dp[1] = 1;
        for (int i = 2; i <= n; i++) {
            dp[i] = dp[i - 1] + dp[i - 2];
        }
        return dp[n];
    }

    private static int fib0(int n) {
        assert n >= 0;
        if (n == 0) {
            return 0;
        }
        if (n == 1) {
            return 1;
        }
        int prev = 0, current = 1, next;
        for (int i = 2; i <= n; i++) {
            next = prev + current;
            prev = current;
            current = next;
        }
        return current;
    }
}
