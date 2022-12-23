package de.rockyj

private fun buildRegister(input: List<String>): Map<Int, Int> {
    val register = mutableMapOf<Int, Int>()

    var lstIndex = 0
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

    return register.toMap()
}

private fun part1(input: List<String>) {
    val register = buildRegister(input)

    // println(register)
    println(listOf(20, 60, 100, 140, 180, 220).sumOf { it * (register[it - 1] ?: 0) })
}

private fun part2(input: List<String>) {
    val register = buildRegister(input)
    val crt = listOf(5, 39)

    (0..crt.first()).forEach { row ->
        (0..crt.last()).forEach { col ->
            val idx = (row * 40) + col
            val sprite = register[idx]!!
            // print(idx)
            if (listOf(sprite-1, sprite, sprite+1).contains(col)) {
                print("#")
            } else {
                print(".")
            }
        }
        println("")
    }
}

fun main() {
    val lines = fileToArr("day10_1.txt")
    (part1(lines))
    (part2(lines))
}