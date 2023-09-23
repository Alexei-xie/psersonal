package com.itself.arrays;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 力扣算法模块做题demo
 * https://leetcode.cn/study-plan/algorithms/?progress=xxhe26c2
 *
 * @Author xxw
 * @Date 2022/09/30
 */
public class AlgorithmDemo {
    public static void main(String[] args) {
        int[] nums = {1, 2, 3, 4, 5, 6, 7};
        System.out.println(rotateMain(nums, 3));
        // System.out.println(search(nums,3));

        // System.out.println(firstBadVersion(5));

        // System.out.println(searchInsert(nums, 7));
        // System.out.println(Arrays.toString(rotate(nums,3)));
        // System.out.println(Arrays.toString(moveZeroes(nums)));
    }

    /**
     * 704. 二分查找
     * 数组
     * 二分查找
     *
     * @param nums
     * @param target
     * @return
     */
    public static int search(int[] nums, int target) {
        if (nums.length == 0) {
            return -1;
        }
        int left = 0;
        int right = nums.length - 1;
        while (left <= right) {
            int mid = (left + right) / 2;
            if (nums[mid] > target) {
                right = mid - 1;
            } else if (nums[mid] < target) {
                left = mid + 1;
            } else {
                return mid;
            }
        }
        return -1;
    }

    /**
     * 278. 第一个错误的版本
     * 二分查找
     * 交互
     *
     * @param n
     * @return
     */
    public static int firstBadVersion(int n) {
        int left = 1;
        int right = n;
        while (left < right) {
            // int mid =(left+|right)/2;//如果这样写会造成int类型数据溢出
            int mid = left + (right - left) / 2;
            if (isBadVersion(mid)) {
                right = mid;
            } else {
                left = mid + 1;
            }
        }
        return left;
    }

    private static boolean isBadVersion(int s) {
        return s == 4;
    }

    /**
     * 35. 搜索插入位置
     * 数组
     * 二分查找
     *
     * @param nums
     * @param target
     * @return
     */
    public static int searchInsert(int[] nums, int target) {
        int left = 0;
        int right = nums.length - 1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
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

    /**
     * 977. 有序数组的平方
     * 数组
     * 双指针
     * 排序
     *
     * @param nums
     * @return
     */
    public static int[] sortedSquares(int[] nums) {
        if (nums.length == 0) {
            return nums;
        }
        int[] ans = new int[nums.length];
        for (int i = 0; i < nums.length; ++i) {
            ans[i] = nums[i] * nums[i];
        }
        Arrays.sort(ans);
        return ans;
    }

    /**
     * 189. 轮转数组
     * 数组
     * 数学
     * 双指针
     * 自己通过笨方法一步一步实现，需查看大佬们的题解思路
     *
     * @param nums
     * @param k
     * @return
     */
    public static int[] rotate(int[] nums, int k) {
        if (nums.length <= 1 || k == 0) {
            return nums;
        }
        int more = 0;
        if (k > nums.length) {
            // 进行取余运算
            more = nums.length - k % nums.length;
        } else {
            more = nums.length - k;
        }
        int[] arr = Arrays.copyOf(nums, nums.length);
        int indexStart = more;
        for (int i = 0; i < nums.length; i++) {
            nums[i] = arr[more];
            more++;
            if (more >= nums.length) {
                more = 0;
            }
            if (more == indexStart) {
                break;
            }
        }
        return nums;
    }

    /**
     * 解法2：力扣官方解法
     * 数组翻转
     */
    public static int[] rotateMain(int[] nums, int k) {
        k %= nums.length;
        reverse(nums, 0, nums.length - 1);
        reverse(nums, 0, k - 1);
        reverse(nums, k, nums.length - 1);
        return nums;
    }
    public static void reverse(int[] nums, int start, int end) {
        while (start < end) {
            int temp = nums[start];
            nums[start] = nums[end];
            nums[end] = temp;
            start += 1;
            end -= 1;
        }
    }

    /**
     * 283. 移动零
     * 数组
     * 双指针
     * 没有按照题目要求进行解答，需学习大佬们的题解思路
     *
     * @param nums
     * @return
     */
    public static int[] moveZeroes(int[] nums) {
        if (nums.length <= 1) {
            return nums;
        }
        int[] arr = Arrays.copyOf(nums, nums.length);
        List<Integer> list = new ArrayList<>();
        List<Integer> list1 = new ArrayList<>();
        for (int j : arr) {
            if (j == 0) {
                list.add(j);
            } else {
                list1.add(j);
            }
        }
        if (list.size() == 0) {
            return nums;
        }
        for (int i = 0; i < list1.size(); i++) {
            nums[i] = list1.get(i);
        }
        int size = list1.size();
        for (Integer integer : list) {
            nums[size] = integer;
            size++;

        }
        return nums;
    }
}
