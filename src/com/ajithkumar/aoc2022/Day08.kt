package com.ajithkumar.aoc2022

import com.ajithkumar.utils.*

fun main() {
    fun inputTo2DArray(inputList: List<String>): Array<IntArray> {
        // Create a mutable list to hold the converted values
        val convertedValues = mutableListOf<IntArray>()

        // Loop through the input list
        for (input in inputList) {

            // Split the input string by empty string
            val splitValues = input.split("").slice(1 until inputList.size+1)
            // Convert the split values to integers and add them to the converted values list
            convertedValues.add(splitValues.map { it.toInt() }.toIntArray())
        }
        // Convert the list of converted values to a 2D IntArray and assign it to the resultArray variable
        return convertedValues.toTypedArray()
    }
    fun part1(input: List<String>): Int {
        // convert input: List<String> into a 2D IntArray with empty string as delimiter
        val trees = inputTo2DArray(input)
        // Create a mutable list to hold the visible trees
        val visibleTrees = mutableListOf<Pair<Int, Int>>()

        for (row in trees.indices) {
            for (col in trees[row].indices) {
                // Check if the current tree is visible from the edge of the grid
                val treeVisible = isTreeVisible(row, col, trees)
//                println("$row, $col, $treeVisible")
                if (treeVisible) {
                    // If the tree is visible, add it to the list of visible trees
                    visibleTrees.add(Pair(row, col))
                }
            }
        }
        // Find number of elements in the 2D IntArray whose value is
        return visibleTrees.size
    }

    fun scenicScore(trees: Array<IntArray>, row: Int, col: Int): Int {
        val height = trees[row][col]
        //iterate top
        var topCount = 0
        for(r in (0 until row).reversed()) {
            topCount++
            if(trees[r][col] >= height) {break}
        }
        var bottomCount = 0
        for(r in row+1 until trees.size) {
            bottomCount++
            if(trees[r][col] >= height) {break}
        }

        var leftCount = 0
        for(c in (0 until col).reversed()) {
            leftCount++
            if(trees[row][c] >= height) {break}
        }
        var rightCount = 0
        for(c in col+1 until trees.size) {
            rightCount++
            if(trees[row][c] >= height) {break}
        }
        return topCount*bottomCount*leftCount*rightCount
    }

    fun part2(input: List<String>): Int {
        val trees = inputTo2DArray(input)
        val result = Array(input.size) {IntArray(input.size) {1} }

        for (row in trees.indices) {
            for (col in trees[row].indices) {
                result[row][col] = scenicScore(trees, row, col)
            }
        }

        println(result.contentDeepToString())
        return result.map { rows -> rows.max() }.max()

    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day08_test")
    println(part1(testInput))
    println(part2(testInput))

    val input = readInput("Day08")
    println(part1(input))
    println(part2(input))
}


// Iterate over the rows and columns of the 2D array

// Define the isTreeVisible() function
fun isTreeVisible(row: Int, col: Int, trees: Array<IntArray>): Boolean {
    // Get the height of the current tree
    val height = trees[row][col]
    // Check if the current tree is located on the edge of the grid
    if (row == 0 || row == trees.size - 1 || col == 0 || col == trees[row].size - 1) {
        return true
    }
    // Check if the current tree is visible from the top edge of the grid
    var topVisible = true
    if (row > 0) {
        for (r in 0 until row) {
            if (trees[r][col] >= height) {
                topVisible = false
                break
            }
        }
    }

    // Check if the current tree is visible from the bottom edge of the grid
    var bottomVisible = true
    if (row < trees.size - 1) {
        for (r in row + 1 until trees.size) {
            if (trees[r][col] >= height) {
                bottomVisible = false
                break
            }
        }
    }

    // Check if the current tree is visible from the left edge of the grid
    var leftVisible = true
    if (col > 0) {
        for (c in 0 until col) {
            if (trees[row][c] >= height) {
                leftVisible = false
                break
            }
        }
    }

    // Check if the current tree is visible from the right edge of the grid
    var rightVisible = true
    if (col < trees[row].size - 1) {
        for (c in col + 1 until trees[row].size) {
            if (trees[row][c] >= height) {
                rightVisible = false
                break
            }
        }
    }
    return (topVisible || bottomVisible || rightVisible || leftVisible)
}
