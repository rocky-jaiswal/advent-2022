package de.rockyj

private fun getVisibleTrees(view: List<List<Pair<String, Int>>>): List<Pair<String, Int>> {
    var visibleTrees: List<Pair<String, Int>> = mutableListOf()

    view.forEachIndexed { index1, row ->
        row.forEachIndexed { index2, tree ->
            if (index1 != 0 && index2 != 0 && index1 != view.size - 1 && index2 != row.size - 1) {
                if (row.subList(0, index2).all { it.second < tree.second }) {
                    println(tree)
                    visibleTrees = visibleTrees.plus(tree)
                }
            }
        }
    }
    println("--")
    return visibleTrees
}

private fun part1(input: List<String>) {
    val cols = input.first().split("").filter { it != "" }.size

    val trees = input
        .map { it.trim().split("").filter { it != "" } }
        .mapIndexed { x, row -> row.mapIndexed { y, t -> Pair("$x-$y", t.toInt()) } }

    // println(input)
    // println(trees)

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
}