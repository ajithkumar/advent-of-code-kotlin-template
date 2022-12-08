package com.ajithkumar.aoc2022

import com.ajithkumar.utils.*
import java.util.stream.IntStream
import kotlin.streams.toList

fun main() {
    fun stringRangeToSet(stringRange: String): Set<Int> {
        val rangeArr = stringRange.split("-").map(String::toInt)
        val result = IntStream.rangeClosed(rangeArr[0], rangeArr[1]).toList().toSet()
//        println(result)
        return result
    }

    fun part1(input: List<String>): Int {
        val result = input.map { it.split(",") }
            .map { it.map { stringRange -> stringRangeToSet(stringRange) } }
            .count { (it[0].containsAll(it[1]) || it[1].containsAll(it[0])) }
        return result
    }

    fun part2(input: List<String>): Int {
        val result = input.map { it.split(",") }
            .map { it.map { stringRange -> stringRangeToSet(stringRange) } }
            .count { it[0].intersect(it[1]).isNotEmpty() }
        return result
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day04_test")
    val testResult1 = part1(testInput)
    val testResult2 = part2(testInput)
    println(testResult1)
    println(testResult2)
    check(testResult1 == 2)
    check(testResult2 == 4)

    val input = readInput("Day04")
    println(part1(input))
    println(part2(input))

    
}
