package com.itself.arrays;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

/**
 * 二维数组样式：[[1,3],[2,6],[8,10],[15,18]]  中括号中只会有两个值
 * 请你合并所有重叠的区间，并返回 一个不重叠的区间数组，该数组需恰好覆盖输入中的所有区间
 * https://leetcode.cn/leetbook/read/array-and-string/c5tv3/
 * @Author xxw
 * @Date 2022/08/25
 */
public class Demo03 {

    public static void main(String[] args) {
        int[][] arrays = {{3,4},{1,5}};
        int[][] merge = merge(arrays);
        for (int i = 0; i < merge.length; i++) {
            System.out.println(Arrays.toString(merge[i]));
        }
    }
    public static int[][] merge(int[][] intervals) {
        if (intervals.length == 0) {
            return intervals;
        }
        Arrays.sort(intervals, Comparator.comparing(e -> e[0]));//对二维数组的每个数组进行正序排序
        //Vector<int[]> vector = new Vector<>();
        ArrayList<int[]> list = new ArrayList<>();
        int[] arr = intervals[0];//先初始化一个数组，从索引为0的位置开始 即临时空间
        for (int i = 0; i < intervals.length; i++) {
            Arrays.sort(intervals[i]);//对二维数组内里面的数据进行二次排序
            if (arr[1] >= intervals[i][0]) {//比较第一个数组的右边最大值和遍历的数组左边的最小值进行相比较
                arr[1] = Math.max(arr[1], intervals[i][1]);//将初始化的数组的右端最大值换为当前比较的两个数组的最大值
            } else {
                list.add(arr);//把一维数组添加到我们创建的vector数组里
                arr = intervals[i];//重新赋值给一维数组
            }
        }
        list.add(arr);//把最后的一个区间存入到vector
        return list.toArray(new int[list.size()][2]);//把vector转为二维数组返回
    }
}
