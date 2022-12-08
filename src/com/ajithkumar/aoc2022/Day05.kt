package com.ajithkumar.aoc2022

import com.ajithkumar.utils.*
import java.util.*
import java.util.Deque

data class MoveInstruction(val count: Int, val source: Int, val destination: Int)

fun main() {
    fun cratesFromInput(input: List<String>): List<Deque<String>> {
        val inputFirstPartEnd = input.indexOf("")

        val crateInput = input.slice(0 until inputFirstPartEnd)
            .map { it.chunked(4).map(String::trim) }


        val crates = mutableListOf<Deque<String>>()
        repeat(crateInput[0].size) {
            crates.add(ArrayDeque())
        }
        crateInput.reversed().forEachIndexed { inputIndex, crateList ->
            if(inputIndex == 0) return@forEachIndexed
            crateList.forEachIndexed { crateIndex, crateString ->
                if(crateString.isEmpty()) return@forEachIndexed
                val crateChar = crateString.trim('[',']')
                crates[crateIndex].push(crateChar)
            }
        }
        return crates
    }

    fun instructionsFromInput(input: List<String>): List<MoveInstruction> {
        val inputFirstPartEnd = input.indexOf("")
        val instructionsInput = input.slice(inputFirstPartEnd+1 until input.size)


        val instructions = instructionsInput.map { instructionString ->
            val parts = instructionString.split(" ")
            MoveInstruction(parts[1].toInt(), parts[3].toInt(), parts[5].toInt())
        }
        return instructions
    }
    fun part1(input: List<String>): String {
        val crates = cratesFromInput(input)
        val instructions = instructionsFromInput(input)
        instructions.forEach { instruction ->
            repeat(instruction.count) {
                crates[instruction.destination-1].push(crates[instruction.source-1].pop())
            }
        }

        return crates.joinToString("") { it.peek() }

    }

    fun part2(input: List<String>): String {
        val crates = cratesFromInput(input)
        val instructions = instructionsFromInput(input)
        instructions.forEach { instruction ->
            val tempStack = ArrayDeque<String>()
            repeat(instruction.count) { tempStack.push(crates[instruction.source-1].pop()) }
            repeat(instruction.count) { crates[instruction.destination-1].push(tempStack.pop()) }
        }

        return crates.joinToString("") { it.peek() }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day05_test")
    println(part1(testInput))
    println(part2(testInput))

    val input = readInput("Day05")
    println(part1(input))
    println(part2(input))
}
