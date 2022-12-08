package com.ajithkumar.aoc2022

import com.ajithkumar.utils.*
import kotlin.streams.toList

fun main() {
    fun charToPriorityTranslate(char: Int): Int {
        if(char in 97..122)
            return char-96
        if(char in 65..90)
            return char-64+26
        return 0
    }

    fun findIntersection(splitStrings: List<String>): Set<Int> {
        val initialAcc = splitStrings[0].chars().toList().toSet()
        return splitStrings.fold(initialAcc) { acc, s -> acc.intersect(s.chars().toList().toSet()) }
    }

    fun part1(input: List<String>): Int {
        return input.map { rucksackString -> rucksackString.chunked(rucksackString.length/2)}
            .map { splitStrings -> findIntersection(splitStrings) }
            .sumOf { intersection -> charToPriorityTranslate(intersection.toList()[0]) }
    }

    fun part2(input: List<String>): Int {
        return input.chunked(3)
            .map { chunkedStrings -> findIntersection(chunkedStrings) }
            .sumOf { intersection -> charToPriorityTranslate(intersection.toList()[0]) }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day03_test")
    check(testInput.size == 6)
    println(part1(testInput))
    println(part2(testInput))

    val input = readInput("Day03")
    println(part1(input))
    println(part2(input))
}
