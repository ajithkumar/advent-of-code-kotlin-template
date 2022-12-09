package com.ajithkumar.aoc2022

import com.ajithkumar.utils.*
import kotlin.math.absoluteValue

class Knot(var X: Int, var Y: Int) {
    fun moveAlone(direction: String) {
        when(direction) {
            "L" -> X-=1
            "R" -> X+=1
            "U" -> Y+=1
            "D" -> Y-=1
        }
    }

    fun moveLinked(leadingKnot: Knot): Boolean {
        val xDiff = (leadingKnot.X - this.X)
        val yDiff = (leadingKnot.Y - this.Y)
        if(xDiff.absoluteValue > 1 || yDiff.absoluteValue > 1) {
            if(xDiff.absoluteValue == 2 && yDiff.absoluteValue == 2) {
                X = leadingKnot.X + (if(xDiff < 0)  1 else -1)
                Y = leadingKnot.Y + (if(yDiff < 0)  1 else -1)
            } else if(xDiff.absoluteValue == 2) {
                X = leadingKnot.X + (if(xDiff < 0)  1 else -1)
                Y = if(yDiff.absoluteValue > 0) leadingKnot.Y else this.Y
            } else {
                X = if(xDiff.absoluteValue > 0) leadingKnot.X else this.X
                Y = leadingKnot.Y + (if(yDiff < 0)  1 else -1)
            }
            return true
        }
        return false
    }

    fun currentPosition(): Pair<Int, Int> {
        return Pair(X, Y)
    }
}

class Rope(private val numberOfKnots: Int) {
    private val knots = buildList {
        repeat(numberOfKnots) {
            add(Knot(0,0))
        }
    }
    val tailVisited = mutableSetOf(knots.last().currentPosition())
    fun moveHead(direction: String, steps: Int) {
        repeat(steps) {
            moveHeadOnce(direction)
            tailVisited.add(knots.last().currentPosition())
        }
    }

    private fun moveHeadOnce(direction: String) {
        knots[0].moveAlone(direction)
        for(i in 1 until knots.size) {
            val moved = knots[i].moveLinked(knots[i-1])
            if(!moved) break
        }
    }
}

fun main() {
    fun part1(input: List<String>): Int {
        val knotsInRope = 2

        val moves = input.map { it.split(" ") }
        val rope = Rope(knotsInRope)
        moves.forEach { (dir, steps) ->
            rope.moveHead(dir, steps.toInt())
        }
        return rope.tailVisited.size
    }

    fun part2(input: List<String>): Int {
        val knotsInRope = 10

        val moves = input.map { it.split(" ") }
        val rope = Rope(knotsInRope)
        moves.forEach { (dir, steps) ->
            rope.moveHead(dir, steps.toInt())
        }
        return rope.tailVisited.size
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day09_test")
    println(part1(testInput))

    val testInput2 = readInput("Day09_test2")
    println(part2(testInput2))

    val input = readInput("Day09")
    println(part1(input))
    println(part2(input))
}
