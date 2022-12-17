package de.rockyj

import kotlin.math.pow
import kotlin.math.sqrt

private class Knot {
    var positions = listOf<Pair<Int, Int>>(Pair(0,0))

    constructor() {
    }

    constructor(positions: List<Pair<Int, Int>>) {
        this.positions = positions
    }
}

private fun distance(p1: Pair<Int, Int>, p2: Pair<Int, Int>): Double {
    val d = ((p1.first - p2.first).toDouble()).pow(2) + ((p1.second - p2.second).toDouble()).pow(2)
    return sqrt(d)
}

private fun updateHeadPosition(direction: String, headPositions: List<Pair<Int, Int>>): Pair<Int, Int> {
    if (direction == "R") {
        return Pair(headPositions.last().first + 1, headPositions.last().second)
    }
    if (direction == "L") {
        return Pair(headPositions.last().first - 1, headPositions.last().second)
    }
    if (direction == "U") {
        return Pair(headPositions.last().first, headPositions.last().second + 1)
    }
    if (direction == "D") {
        return Pair(headPositions.last().first, headPositions.last().second - 1)
    }
    throw Exception("Bad direction")
}

private fun updateTailPosition(headPositions: List<Pair<Int, Int>>, tailPositions: List<Pair<Int, Int>>): Pair<Int, Int>? {
     val lastPosHead = headPositions.last()
     val lastPosTail = tailPositions.last()

    // println(headPosition)
    // println(tailPosition)
    // println("=======")

    if (lastPosHead.first == lastPosTail.first && lastPosHead.second == lastPosTail.second) {
        return null
    }
    if (lastPosHead.first == lastPosTail.first) { // same row
        if (distance(lastPosHead, lastPosTail) > sqrt(2.0)) {
            val op = if (lastPosHead.second > lastPosTail.second) 1 else - 1
            return Pair(lastPosTail.first, lastPosTail.second + op)

        }
    }
    if (lastPosHead.second == lastPosTail.second) { // same col
        if (distance(lastPosHead, lastPosTail) > sqrt(2.0)) {
            val op = if (lastPosHead.first > lastPosTail.first) 1 else - 1
            return Pair(lastPosTail.first + op, lastPosTail.second)

        }
    }
    // diagonal
    if (lastPosHead.first > lastPosTail.first && lastPosHead.second > lastPosTail.second) {
        if (distance(lastPosHead, lastPosTail) > sqrt(2.0)) {
            return Pair(lastPosTail.first + 1, lastPosTail.second + 1)
        }
    }
    if (lastPosHead.first < lastPosTail.first && lastPosHead.second < lastPosTail.second) {
        if (distance(lastPosHead, lastPosTail) > sqrt(2.0)) {
            return Pair(lastPosTail.first - 1, lastPosTail.second - 1)
        }
    }
    if (lastPosHead.first > lastPosTail.first && lastPosHead.second < lastPosTail.second) {
        if (distance(lastPosHead, lastPosTail) > sqrt(2.0)) {
            return Pair(lastPosTail.first + 1, lastPosTail.second - 1)
        }
    }
    if (lastPosHead.first < lastPosTail.first && lastPosHead.second > lastPosTail.second) {
        if (distance(lastPosHead, lastPosTail) > sqrt(2.0)) {
            return Pair(lastPosTail.first - 1, lastPosTail.second + 1)
        }
    }
    return null
}

private fun solve(input: List<String>) {
    val directions = input.map { it.trim().split(Regex("""\s""")) }

    // val rope = MutableList(2){ Knot() } // part 1
    val rope = MutableList(10){ Knot() }

    directions.forEach { direction ->
        repeat((1..direction.last().toInt()).count()) {
            var headPositions = rope[0].positions
            headPositions = headPositions.plus(updateHeadPosition(direction.first(), headPositions))
            rope[0] = Knot(headPositions)

            (1 until rope.size).forEach { knotIndex ->
                val newTail = updateTailPosition(rope[knotIndex-1].positions, rope[knotIndex].positions)
                if (newTail != null) {
                    rope[knotIndex].positions = rope[knotIndex].positions.plus(newTail)
                    rope[knotIndex] = Knot(rope[knotIndex].positions)
                }
            }
        }
    }

    println(rope.last().positions.distinct().count())
}

fun main() {
    val lines = fileToArr("day9_2.txt")
    (solve(lines))
}