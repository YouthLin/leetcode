package com.youthlin.leetcode.arrays;

import java.util.Arrays;

/**
 * @author youthlin.chen @ 2020-05-21 22:03:48
 */
public class Sort {
    public static void main(String[] args) {
        int[] ints = {1, 5, 9, 4, 6, 2, 9, 8, 3, 0, 7};
        qsort(ints);
        System.out.println(Arrays.toString(ints));
    }

    public static void qsort(int[] arr) {
        qsort(arr, 0, arr.length - 1);
    }

    private static void qsort(int[] arr, int left, int right) {
        if (left < right) {
            int index = partition(arr, left, right);
            qsort(arr, left, index - 1);
            qsort(arr, index + 1, right);
        }
    }

    private static int partition(int[] arr, int left, int right) {
        int base = arr[left];
        int i = left, j = right;
        while (i < j) {
            //从右往左找第一个比基数小的
            while (arr[j] >= base && i < j) {
                j--;
            }
            //从左往右找第一个比基数大的
            while (arr[i] <= base && i < j) {
                i++;
            }
            swap(arr, i, j);
        }
        // 把基数放到中间来
        swap(arr, left, i);
        return i;
    }

    private static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
}
