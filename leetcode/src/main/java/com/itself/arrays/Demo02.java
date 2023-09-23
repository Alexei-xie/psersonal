package com.itself.arrays;

/**
 * 给定一个排序数组和一个目标值，在数组中找到目标值，并返回其索引。如果目标值不存在于数组中，返回它将会被按顺序插入的位置
 * 请必须使用时间复杂度为 O(log n) 的算法
 * https://leetcode.cn/leetbook/read/array-and-string/cxqdh/
 * @Author xxw
 * @Date 2022/08/24
 */
public class Demo02 {
    public static void main(String[] args) {
            int[] ints = {1,3,5,6};
            int result = method(ints,5);
            System.out.println(result);
    }

    /**
     * 暴力遍历方法 时间复杂度为 O(N)
     */
    public static int search (int[] nums, int target){
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] >= target){
                return i;
            }
        }
        return nums.length;
    }

    /**
     * 时间复杂度：O(log n)
     * 空间复杂度：O(1)
     */
    public static int method(int[] nums, int target) {
        int left = 0;
        int right = nums.length - 1;
        while (left <= right) {
            int mid = (right + left) / 2;
            if (nums[mid] < target) {
                left = mid + 1;
            } else if (nums[mid] > target) {
                right = mid - 1;
            } else {
                return mid;
            }
        }
        return left;
    }
}
