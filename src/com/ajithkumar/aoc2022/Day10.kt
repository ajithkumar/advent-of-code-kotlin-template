package com.ajithkumar.aoc2022

import com.ajithkumar.utils.*

class CPU(input: List<String>) {
    private var cyclesPending = 0
    var registerValue = 1
    private var pendingCommand = {}
    private var queue = ArrayDeque(input)

    fun execute() {
        if(cyclesPending > 0) {
            cyclesPending--
            pendingCommand.invoke()
        }
        else {
            val command = queue.removeFirst()
            if(command != "noop") {
                pendingCommand = {
                    val addParam = command.split(" ")[1].toInt()
                    registerValue += addParam
                }
                cyclesPending+=1
            }
        }
    }
}

class CRT() {
    private val screenWidth = 40
    private val screenHeight = 6
    private var pixels = Array(screenHeight) {CharArray(screenWidth) { '.' } }
    private var crtCycle = 0
    fun draw(spriteMidPos: Int) {
        val spritePixels = setOf(spriteMidPos-1, spriteMidPos, spriteMidPos+1)
        val row = crtCycle/screenWidth
        val column = crtCycle%screenWidth
        if(column in spritePixels) {
            pixels[row][column] = '#'
        }
        crtCycle++
    }

    fun image() = pixels.joinToString("\n") { String(it) }
}

fun main() {
    fun part1(input: List<String>): Int {
        val signalCycles = (20..220 step 40).toSet()
        var totalSignalStrength = 0
        val cpu = CPU(input)
        for(cycle in 1..220) {
            if(cycle in signalCycles) {
                val signalStrength = cycle*cpu.registerValue
                totalSignalStrength += signalStrength
            }
            cpu.execute()
        }

        return totalSignalStrength
    }

    fun part2(input: List<String>): String {
        val cpu = CPU(input)
        val crt = CRT()
        for(cycle in 1..240) {
            crt.draw(cpu.registerValue)
            cpu.execute()
        }
        return crt.image()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day10_test")
    println(part1(testInput))
    println(part2(testInput))

    val input = readInput("Day10")
    println(part1(input))
    println(part2(input))

    
}
