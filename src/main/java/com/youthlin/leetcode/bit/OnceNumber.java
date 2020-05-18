package com.youthlin.leetcode.bit;

import java.util.Arrays;

/**
 * 给定一个非空整数数组，除了某个元素只出现一次以外，其余每个元素均出现两次。找出那个只出现了一次的元素。
 * <p>
 * 说明：
 * <p>
 * 你的算法应该具有线性时间复杂度。 你可以不使用额外空间来实现吗？
 * <p>
 * 示例 1:
 * <p>
 * 输入: [2,2,1]
 * 输出: 1
 * <p>
 * 示例 2:
 * <p>
 * 输入: [4,1,2,1,2]
 * 输出: 4
 *
 * @author youthlin.chen @ 2020-05-18 21:23:31
 */
public class OnceNumber {
    /*** 异或技巧：异或一个数两次会抵消 */
    public int singleNumber(int[] nums) {
        int len = nums.length;
        int result = 0;
        for (int i = 0; i < len; i++) {
            result ^= nums[i];
        }
        return result;
    }

    /*** 先排序 两个两个看 */
    public int singleNumberBad(int[] nums) {
        int len = nums.length;
        if (len < 1) {
            throw new IllegalArgumentException();
        }
        if (len == 1) {
            return nums[0];
        }

        Arrays.sort(nums);
        for (int i = 0; i < nums.length - 1; i += 2) {
            if (nums[i] != nums[i + 1]) {
                return nums[i];
            }
        }
        return nums[len - 1];
    }

}
