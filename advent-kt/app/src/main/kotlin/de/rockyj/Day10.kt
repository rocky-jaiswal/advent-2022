package de.rockyj

private fun part1(input: List<String>) {
    var lstIndex = 0

    val register = mutableMapOf<Int, Int>()
    register[lstIndex] = 1

    input.map { it.trim().split(Regex("""\s""")) }.forEach { instruction ->
        val ins = instruction.first()
        lstIndex += 1

        if (ins == "noop") {
            register[lstIndex] = register[lstIndex - 1]!!
        }
        if (ins == "addx") {
            val num = instruction.last().toInt()
            register[lstIndex] = register[lstIndex - 1]!!
            lstIndex += 1
            register[lstIndex] = register[lstIndex - 1]!! + num
        }
    }

    // println(register)
    println(listOf(20, 60, 100, 140, 180, 220).sumOf { it * (register[it - 1] ?: 0) })
}

fun main() {
    val lines = fileToArr("day10_1.txt")
    (part1(lines))
}