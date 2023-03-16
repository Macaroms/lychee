package com.lychee.service;

/**
 * @author jiangwei97@aliyun.com
 * @since 2022/12/16 18:02
 */
public class Test {

    public static void main(String[] args) {
        int[] arr = {1,2,3,4,5,6,7,8,9};
        System.out.println(getIndex(arr, 2));
    }

    public static int getIndex(int[] arr, int value){
        int low = 0;
        int high = arr.length - 1;
        while (low <= high){
            int mid = (low + high)/2;
            if (value == arr[mid]){
                return mid;
            }
            if (value > arr[mid]){
                low = mid + 1;
            }
            if (value < arr[mid]){
                high = mid - 1;
            }
        }
        return -1;
    }


}
