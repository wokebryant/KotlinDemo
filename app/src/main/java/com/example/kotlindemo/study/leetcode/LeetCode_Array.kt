package com.example.kotlindemo.study.leetcode

import java.util.*
import kotlin.system.measureTimeMillis

/**
 * @Author: LuoJia
 * @Date: 2022-02-16
 * @Description: LeetCode之数组
 */

private val testIntArray1 = intArrayOf(1, 7, 3, 6, 5, 6)
private val testIntArray2 = intArrayOf(1, 3, 5, 6)
private val testIntArray3 = arrayOf(intArrayOf(1, 4), intArrayOf(0, 2), intArrayOf(3, 5))

fun main() {
    val cost = measureTimeMillis {
//        pivotIndex(testIntArray1)
//        searchInsert(testIntArray2)
        merge(testIntArray3)
    }
    println("TimeCost: $cost ms")
}

/**
 *  寻找数组中心索引 Easy
 *  From: https://leetcode-cn.com/problems/find-pivot-index/
 */
private fun pivotIndex(nums: IntArray): Int {
    // sum（）方法耗时
//    var total = nums.sum()
    var total = 0
    for (i in nums.indices) {
        total += nums[i]
    }
    var sum = 0
    for (i in nums.indices) {
        if (2 * sum + nums[i] == total) {
            return i
        }
        sum += nums[i]
    }
    return -1
}

/**
 *  搜索插入位置 Easy
 *  From: https://leetcode-cn.com/problems/search-insert-position/
 */
private fun searchInsert(nums: IntArray, target: Int = 7): Int {
    // 暴力解法, 时间复杂度为O(n)
    for (i in nums.indices) {
        if (nums[i] >= target) {
            return i
        }
    }

    // 二分查找法, 时间复杂度为O(log n)
    var left = 0
    var right = nums.size - 1
    while (left <= right) {
        val middle = (left + right) / 2
        when {
            nums[middle] == target -> {
                return middle
            }
            nums[middle] > target -> {
                right = middle - 1
            }
            else -> {
                left = middle + 1
            }
        }
    }
//    return nums.size
    return left
}

/**
 *  合并数组 Medium
 *  From: https://leetcode-cn.com/problems/merge-intervals/
 */
private fun merge(intervals: Array<IntArray>): Array<IntArray> {
    // sortBy拓展方法耗时
//    intervals.sortBy { it[0] }

    // 按第一维元素比较一维数组
    Arrays.sort(intervals) { a, b -> a[0] - b[0] }

    val newIntervals = ArrayList<IntArray>()
    var mergeArray = intervals[0]
    for (i in 1 until intervals.size) {
        if (mergeArray[1] >= intervals[i][0]) {
            mergeArray[1] = maxOf(mergeArray[1], intervals[i][1])
        } else {
            newIntervals.add(mergeArray)
            mergeArray = intervals[i]
        }
    }
    newIntervals.add(mergeArray)

    return newIntervals.toTypedArray()
}

/**
 *  旋转矩阵 Medium
 *  From: https://leetcode-cn.com/problems/rotate-image/
 */
private fun rotate(matrix: Array<IntArray>) {
    val newMatrix = Array(matrix.size) { IntArray(matrix.size) }
    for (i in matrix.indices) {
        val row = matrix[i]
        for (j in row.indices) {
            newMatrix
        }
    }
}