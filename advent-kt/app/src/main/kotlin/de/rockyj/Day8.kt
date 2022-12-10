package de.rockyj

private fun getVisibleTrees(view: List<List<Pair<String, Int>>>): List<Pair<String, Int>> {
    var visibleTrees: List<Pair<String, Int>> = mutableListOf()

    view.forEachIndexed { index1, row ->
        row.forEachIndexed { index2, tree ->
            if (index1 != 0 && index2 != 0 && index1 != view.size - 1 && index2 != row.size - 1) {
                if (row.subList(0, index2).all { it.second < tree.second }) {
                    // println(tree)
                    visibleTrees = visibleTrees.plus(tree)
                }
            }
        }
    }
    // println("--")
    return visibleTrees
}

private fun buildTrees(input: List<String>): List<List<Pair<String, Int>>> {
    return input
        .map { it.trim().split("").filter { it != "" } }
        .mapIndexed { x, row -> row.mapIndexed { y, t -> Pair("$x-$y", t.toInt()) } }
}

private fun part2(input: List<String>) {
    val trees = buildTrees(input)

    val numOfCols = trees.first().size
    val numOfRows = trees.size

    val _mapOfTrees = mutableMapOf<String, Int>()

    trees.forEach() { row ->
        row.forEach { tree ->
            _mapOfTrees.put(tree.first, tree.second)
        }
    }

    val mapOfTrees = _mapOfTrees.toMap()

    fun findNeighbourScore(tKey: String): Int {
        val x = tKey.split("-").first().toInt()
        val y = tKey.split("-").last().toInt()

        // edge cases
        if (x == 0 || y == 0 || x == numOfRows - 1 || y == numOfCols - 1) {
            return 0
        }

        val left = (0 until y).map { Pair(x, it) }
        val right = (y+1 until numOfCols).map { Pair(x, it) }
        val top = (0 until x).map { Pair(it, y) }
        val bottom = (x+1 until numOfRows).map { Pair(it, y) }

        val myHeight = mapOfTrees[tKey]!!

        var c1 = 0
        var stopLeft = false
        left.map { mapOfTrees["${it.first}-${it.second}"] }.reversed().forEach {
            if (!stopLeft) {
                c1 += 1
            }
            if (it!! >= myHeight) {
                stopLeft = true
            }
        }

        var c2 = 0
        var stopRight = false
        right.map { mapOfTrees["${it.first}-${it.second}"] }.forEach {
            if (!stopRight) {
                c2 += 1
            }
            if (it!! >= myHeight) {
                stopRight = true
            }
        }

        var c3 = 0
        var stopTop = false
        top.map { mapOfTrees["${it.first}-${it.second}"] }.reversed().forEach {
            if (!stopTop) {
                c3 += 1
            }
            if (it!! >= myHeight) {
                stopTop = true
            }
        }

        var c4 = 0
        var stopBottom = false
        bottom.map { mapOfTrees["${it.first}-${it.second}"] }.forEach {
            if (!stopBottom) {
                c4 += 1
            }
            if (it!! >= myHeight) {
                stopBottom = true
            }
        }

        return c1 * c2 * c3 * c4
    }

    println(mapOfTrees.keys.map { findNeighbourScore(it) }.maxOf { it })
}

private fun part1(input: List<String>) {
    val cols = input.first().split("").filter { it != "" }.size
    val trees = buildTrees(input)

    val treesFromLeft = trees
    val treesFromRight = trees.map { it.reversed() }
    val treesFromTop = (0 until cols).map { col -> trees.map { r -> r[col] }  }
    val treesFromBottom = treesFromTop.map { it.reversed() }

    val internalTrees = listOf(treesFromLeft, treesFromRight, treesFromTop, treesFromBottom)
        .map { getVisibleTrees(it) }
        .flatten()
        .distinctBy { it.first }

    val externalTrees = input.size + input.size + cols + cols - 4

    // println(internalTrees)
    println(internalTrees.size + externalTrees)
}

fun main() {
    val lines = fileToArr("day8_2.txt")
    (part1(lines))
    (part2(lines))
}