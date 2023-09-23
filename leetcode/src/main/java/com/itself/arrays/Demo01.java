package com.itself.arrays;

/**
 * 给你一个整数数组 nums ，请计算数组的 中心下标
 * https://leetcode.cn/leetbook/read/array-and-string/yf47s/
 * @Author xxw
 * @Date 2022/08/23
 */
public class Demo01 {
    public static void main(String[] args) {
        int[] ints = {2, 1, -1};
        int result = pivotIndex(ints);
        System.out.println(result);
    }
    public static int pivotIndex(int[] nums) {
        //数组总合
        int sum = 0;
        //计算相等的临时变量
        int num = 0;
        for (int i = 0; i < nums.length; i++) {
            //计算数组总合
            sum += nums[i];
        }
        if (sum-nums[0] == 0){
            return 0;
        }
        //todo 可优化
        for (int j = 0; j < nums.length; j++) {
            num+=nums[j];
            //防止索引越界
            if ((j+1 < nums.length)&&(num == sum-num-nums[j+1])){
                return j+1;
            }
        }
        return  -1;
    }
}
