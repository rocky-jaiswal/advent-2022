package de.rockyj

import kotlin.math.pow
import kotlin.math.sqrt

private var headPosition = listOf<Pair<Int, Int>>(Pair(0,0))
private var tailPosition = listOf<Pair<Int, Int>>(Pair(0,0))

private fun distance(p1: Pair<Int, Int>, p2: Pair<Int, Int>): Double {
    val d = ((p1.first - p2.first).toDouble()).pow(2) + ((p1.second - p2.second).toDouble()).pow(2)
    return sqrt(d)
}

private fun updateTailPosition() {
    val lastPosHead = headPosition.last()
    val lastPosTail = tailPosition.last()

    // println(headPosition)
    // println(tailPosition)
    // println("=======")

    if (lastPosHead.first == lastPosTail.first && lastPosHead.second == lastPosTail.second) {
        return
    }
    if (lastPosHead.first == lastPosTail.first) { // same row
        if (distance(lastPosHead, lastPosTail) > sqrt(2.0)) {
            val op = if (lastPosHead.second > lastPosTail.second) 1 else - 1
            tailPosition = tailPosition.plus(Pair(lastPosTail.first, lastPosTail.second + op))
            return
        }
    }
    if (lastPosHead.second == lastPosTail.second) { // same col
        if (distance(lastPosHead, lastPosTail) > sqrt(2.0)) {
            val op = if (lastPosHead.first > lastPosTail.first) 1 else - 1
            tailPosition = tailPosition.plus(Pair(lastPosTail.first + op, lastPosTail.second))
            return
        }
    }
    // diagonal
    if (lastPosHead.first > lastPosTail.first && lastPosHead.second > lastPosTail.second) {
        if (distance(lastPosHead, lastPosTail) > sqrt(2.0)) {
            tailPosition = tailPosition.plus(Pair(lastPosTail.first + 1, lastPosTail.second + 1))
            return
        }
    }
    if (lastPosHead.first < lastPosTail.first && lastPosHead.second < lastPosTail.second) {
        if (distance(lastPosHead, lastPosTail) > sqrt(2.0)) {
            tailPosition = tailPosition.plus(Pair(lastPosTail.first - 1, lastPosTail.second - 1))
            return
        }
    }
    if (lastPosHead.first > lastPosTail.first && lastPosHead.second < lastPosTail.second) {
        if (distance(lastPosHead, lastPosTail) > sqrt(2.0)) {
            tailPosition = tailPosition.plus(Pair(lastPosTail.first + 1, lastPosTail.second - 1))
            return
        }
    }
    if (lastPosHead.first < lastPosTail.first && lastPosHead.second > lastPosTail.second) {
        if (distance(lastPosHead, lastPosTail) > sqrt(2.0)) {
            tailPosition = tailPosition.plus(Pair(lastPosTail.first - 1, lastPosTail.second + 1))
            return
        }
    }
}

private fun part1(input: List<String>) {
    val directions = input.map { it.trim().split(Regex("""\s""")) }

    directions.forEach { direction ->
        // println(direction)
        if (direction.first() == "R") {
            repeat((1..direction.last().toInt()).count()) {
                headPosition = headPosition.plus(Pair(headPosition.last().first + 1, headPosition.last().second))
                updateTailPosition()
            }
        }
        if (direction.first() == "L") {
            repeat((1..direction.last().toInt()).count()) {
                headPosition = headPosition.plus(Pair(headPosition.last().first - 1, headPosition.last().second))
                updateTailPosition()
            }
        }
        if (direction.first() == "U") {
            repeat((1..direction.last().toInt()).count()) {
                headPosition = headPosition.plus(Pair(headPosition.last().first, headPosition.last().second + 1))
                updateTailPosition()
            }
        }
        if (direction.first() == "D") {
            repeat((1..direction.last().toInt()).count()) {
                headPosition = headPosition.plus(Pair(headPosition.last().first, headPosition.last().second - 1))
                updateTailPosition()
            }
        }
    }
    println(tailPosition.distinct().count())
}

fun main() {
    val lines = fileToArr("day9_2.txt")
    (part1(lines))
}