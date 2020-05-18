package com.youthlin.leetcode.bit;

import java.util.HashMap;
import java.util.Map;

/**
 * @author youthlin.chen @ 2020-05-18 21:40:23
 */
public class MajorityElement {
    public static void main(String[] args) {
        System.out.println(new MajorityElement()
                .majorityElement(new int[]{3, 2, 3}));
        System.out.println(findByMoore(new int[]{3, 3, 2, 2, 1, 2, 1}));
    }

    public int majorityElement(int[] nums) {
        int len = nums.length;
        int minCount = len / 2;
        Map<Integer, Integer> map = new HashMap<>(len);
        for (int num : nums) {
            int count = map.getOrDefault(num, 0);
            if (count >= minCount) {
                return num;
            }
            map.put(num, ++count);
        }
        return 0;
    }

    /**
     * find by 摩尔算法
     * 众数 +1
     * 非众数 -1
     * 众数肯定大于 n/2，所以最终结果肯定是大于0的。
     * 每当等于0时，就把当前的数设置为众数。
     */
    private static int findByMoore(int[] nums) {
        int count = 0;
        int candidate = 0;
        for (int num : nums) {
            if (count == 0) {
                candidate = num;
            }

            count += (candidate == num) ? 1 : -1;
        }
        return candidate;
    }


}
