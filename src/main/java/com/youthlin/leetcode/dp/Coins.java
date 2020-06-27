package com.youthlin.leetcode.dp;

import java.util.HashMap;
import java.util.Map;

/**
 * @author youthlin.chen @ 2020-06-27 16:01:09
 */
public class Coins {
    public static void main(String[] args) {
        // 3个硬币 11 = 5+5+1
        System.out.println(coinChange(new int[]{1, 2, 5}, 11));

    }

    /*** k 种硬币，面值是 c1,c2,...,ck 每种数量无限。需要凑出 amount 金额，最少需要几个硬币。无解返回 -1 */
    private static int coinChange(int[] coins, int amount) {
        // base case： amount=0 返回 0
        // 状态：剩余需要凑出的金额
        // 选择：每次选哪种硬币
        // dp 定义：dp[i]==凑出金额i需要的硬币个数。
        // dp[0]=0
        // dp[i]=min{1+dp[i-ci],ci是各个面值}
        return dp(coins, amount, new HashMap<>(amount));
    }

    private static int dp(int[] coins, int i, Map<Integer, Integer> memo) {
        Integer result = memo.get(i);
        // System.out.println(i + "=>" + result);
        if (result != null) {
            return result;
        }
        if (i == 0) {
            memo.put(i, 0);
            return 0;
        }
        if (i < 0) {
            memo.put(i, -1);
            return -1;
        }
        for (int coin : coins) {
            int subProblem = dp(coins, i - coin, memo);
            // System.out.println((i - coin) + "->" + subProblem);
            if (subProblem == -1) {
                continue;
            }
            if (result == null) {
                result = subProblem + 1;
            } else {
                result = Math.min(result, subProblem + 1);
            }
        }
        if (result == null) {
            result = -1;
        }
        // System.out.println("put " + i + ":" + result);
        memo.put(i, result);
        return result;
    }
}
