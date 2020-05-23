package com.youthlin.leetcode.arrays;

/**
 * @author youthlin.chen @ 2020-05-21 22:30:56
 */
public class Arrays {
    public static void main(String[] args) {
        int[] a = {1, 2, 3, 0, 0, 0};
        int[] b = {2, 5, 6};
        merge1(a, 3, b, b.length);
        System.out.println(java.util.Arrays.toString(a));
        a = new int[]{0};
        b = new int[]{1};
        merge1(a, 0, b, 1);
        System.out.println(java.util.Arrays.toString(a));

        a = new int[]{4, 5, 6, 0, 0, 0};
        b = new int[]{1, 2, 3};
        merge1(a, 3, b, 3);
        System.out.println(java.util.Arrays.toString(a));

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

}
