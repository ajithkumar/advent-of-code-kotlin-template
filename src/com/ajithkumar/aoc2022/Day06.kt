package com.ajithkumar.aoc2022

import com.ajithkumar.utils.*

fun main() {
    fun stringHasRepetition(s: String, range: IntRange): Boolean {
        var charSet = mutableSetOf<Char>()
        range.forEach { index ->
            if(charSet.contains(s[index])) {
                return true
            } else {
                charSet.add(s[index])
            }
        }
        return false
    }

    fun part1(input: String): Int {
        var windowsStart = 0
        var windowEnd = windowsStart+4
        while(windowEnd < input.length) {
            if(stringHasRepetition(input, windowsStart until windowEnd)) {
                windowsStart++
                windowEnd++
                continue
            }
            else {
                break
            }
        }
        return windowEnd
    }

    fun part2(input: String): Int {
        var windowsStart = 0
        var windowEnd = windowsStart+14
        while(windowEnd < input.length) {
            if(stringHasRepetition(input, windowsStart until windowEnd)) {
                windowsStart++
                windowEnd++
                continue
            }
            else {
                break
            }
        }
        return windowEnd
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInputText("Day06_test")
    println(part1(testInput))
    println(part2(testInput))

    val input = readInputText("Day06")
    println(part1(input))
    println(part2(input))

    
}
