package com.youthlin.leetcode.arrays;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * @author youthlin.chen @ 2020-05-21 22:30:56
 */
public class Arrays {
    public static void main(String[] args) {
        int[] nums = {0, 0, 1};
        moveZeroes(nums);
        System.out.println(java.util.Arrays.toString(nums));
    }

    /*** 从 nums1 结尾开始 */
    public static void merge1(int[] nums1, int m, int[] nums2, int n) {
        // 从后往前放
        int index = m + n - 1;
        int p1 = m - 1, p2 = n - 1;
        while (p2 >= 0 && p1 >= 0) {
            /* nums1 = [1,2,3,0,0,0], m = 3
             * nums2 = [2,5,6],       n = 3
             * */
            if (nums2[p2] >= nums1[p1]) {
                nums1[index--] = nums2[p2--];
            } else {
                nums1[index--] = nums1[p1--];
            }
        }
        // 4,5,6,0,0,0
        // 1,2,3
        while (p2 >= 0) {
            nums1[index--] = nums2[p2--];
        }
    }

    /**
     * 输入:
     * nums1 = [1,2,3,0,0,0], m = 3
     * nums2 = [2,5,6],       n = 3
     * <p>
     * 输出: [1,2,2,3,5,6]
     */
    public static void merge(int[] nums1, int m, int[] nums2, int n) {
        int i = 0, j = 0;
        //依次处理第二个数组
        while (j < n) {
            //找对应第一个数组的下标，需要注意 i<m+j 以免越界：m+j 是原先第一个数组加上第二个数组挪过来的个数
            while (i < m + j && nums1[i] <= nums2[j]) {
                i++;
            }
            //插入到第一个数组
            insert(nums1, i, nums2[j]);
            //处理第二个数组下一个元素
            j++;
        }
    }

    private static void insert(int[] nums, int index, int value) {
        // len=6,index=0
        // 4,5,6,0,0,0
        // 4,4,5,6,0,0
        // srcPos 从哪个下标开始移动，destPos 移动到的下标，length 移动的元素个数
        System.arraycopy(nums, index, nums, index + 1, nums.length - 1 - index);
        nums[index] = value;
    }

    /**
     * 旋转数组：给定一个数组，将数组中的元素向右移动 k 个位置，其中 k 是非负数。
     * <p>
     * 作者：LeetCode
     * 链接：https://leetcode-cn.com/problems/rotate-array/solution/xuan-zhuan-shu-zu-by-leetcode/
     * 来源：力扣（LeetCode）
     * 著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
     */
    public static void rotate(int[] nums, int k) {
        reverse(nums, 0, nums.length - 1);
        k %= nums.length;
        reverse(nums, 0, k - 1);
        reverse(nums, k, nums.length - 1);
    }

    /**
     * 假设 n=7n=7 且 k=3 。
     * <p>
     * 原始数组                  : 1 2 3 4 5 6 7
     * 反转所有数字后             : 7 6 5 4 3 2 1
     * 反转前 k 个数字后          : 5 6 7 4 3 2 1
     * 反转后 n-k 个数字后        : 5 6 7 1 2 3 4 --> 结果
     * <p>
     * 作者：LeetCode
     * 链接：https://leetcode-cn.com/problems/rotate-array/solution/xuan-zhuan-shu-zu-by-leetcode/
     * 来源：力扣（LeetCode）
     * 著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
     */
    public static void reverse(int[] nums, int start, int end) {
        while (start < end) {
            int temp = nums[start];
            nums[start] = nums[end];
            nums[end] = temp;
            start++;
            end--;
        }
    }

    /**
     * 将数组的0移动到末尾，保持其他元素到相对顺序
     * 0 1 2 0 3 4
     * 1 2 3 4 0 0
     */
    public static void moveZeroes(int[] nums) {
        int zeros = 0;
        int len = nums.length;
        // 0,1,0,3,12
        for (int i = 0; i < len - 1 - zeros; ) {
            if (nums[i] == 0) {
                zeros++;
                System.arraycopy(nums, i + 1, nums, i, len - 1 - i);
                // 1 0 3 12 12   o=1
                // 1 3 12 12 12  o=2
            } else {
                i++;
            }
        }
        // 1 3 12 0 0
        for (int i = nums.length - 1; i > nums.length - 1 - zeros; i--) {
            nums[i] = 0;
        }
    }

    public static void moveZeroes1(int[] nums) {
        int nonZeroIndex = 0;
        int len = nums.length;
        // 0 1 0 3 4
        for (int i = 0; i < len; i++) {
            if (nums[i] != 0) {
                nums[nonZeroIndex++] = nums[i];
                // 1 1 0 3 4 . i = 1,nonZeroIndex=0, ++> 1
                // 1 3 0 3 4 . i = 3,nonZeroIndex=1, ++> 2
                // 1 3 4 3 4 . i = 4,nonZeroIndex=2, ++> 3
            }
        }
        // 1 3 4 0 0
        for (int i = nonZeroIndex; i < len; i++) {
            nums[i] = 0;
        }
    }

    private static class Shuffle {
        private static final Random RANDOM = new Random(System.currentTimeMillis());
        private final int[] original;
        private int[] nums;

        Shuffle(int[] input) {
            this.original = input.clone();
            this.nums = input.clone();
        }

        /*** 洗牌算法 i = 0 时 randIndex 有 len 种可能， i = 1 时有 len - 1 种，一共 n！ 就是所有的排列组合情况 */
        private int[] shuffle() {
            for (int i = 0, len = nums.length; i < len; i++) {
                int randIndex = randClosedOpen(i, len);
                swap(nums, i, randIndex);
            }
            return nums.clone();
        }

        private int[] reset() {
            return original.clone();
        }

        private static void swap(int[] arr, int i, int j) {
            int tmp = arr[i];
            arr[i] = arr[j];
            arr[j] = tmp;
        }

        private static int randClosedOpen(int min, int max) {
            // nextInt(n) => [0,n)
            // nextInt(max)+min => [0,max)+min => [min,min+max)
            // nextInt(max-min) => [0,max-min)
            // nextInt(max-min)+min => [0,max-min)+min => [min,max)
            // nextInt(max-min+1)+min => [min,max]
            return RANDOM.nextInt(max - min) + min;
        }
    }

    /*** 数组交集 出现次数一致的元素 https://leetcode-cn.com/problems/intersection-of-two-arrays-ii/ */
    public static int[] intersect(int[] nums1, int[] nums2) {
        // nums -> count
        Map<Integer, Integer> map = new HashMap<>();
        for (int num : nums1) {
            int old = map.getOrDefault(num, 0);
            map.put(num, old + 1);
        }
        List<Integer> result = new LinkedList<>();
        for (int num : nums2) {
            int count = map.getOrDefault(num, 0);
            if (count > 0) {
                result.add(num);
                map.put(num, count - 1);
            }
        }
        return result.stream().mapToInt(x -> x).toArray();
    }

}
