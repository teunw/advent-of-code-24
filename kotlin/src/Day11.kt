val stoneMap = mutableMapOf<String, ULong>()

fun blinkStone(stone: String, times: Int): ULong {
    if (times == 0) return 1UL
    val key = "$stone|$times"
    val alreadyFound = stoneMap[key]
    if (alreadyFound != null) return alreadyFound
    val newValue = when {
        stone == "0" -> return blinkStone("1", times - 1)
        stone.length % 2 == 0 ->
            blinkStone(stone.substring(0, stone.length / 2).toULong().toString(), times - 1) +
                    blinkStone(stone.substring(stone.length / 2, stone.length).toULong().toString(), times - 1)
        else -> blinkStone((stone.toULong() * 2024UL).toString(), times - 1)
    }
    stoneMap[key] = newValue
    return newValue
}
fun main() {
    fun part1(input: String, blinks: Int): ULong {
        return input.trim()
            .split(' ')
            .map { blinkStone(it, blinks) }
            .sum()
    }

    // Test if implementation meets criteria from the description, like:
    check(part1(readInput("Day11_example"), 25) == 55312UL)
//    check(part2(readInput("Day11_example")) == 6)

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day11")
    println("Part 1 = ${part1(input, 25)}")
    println("Part 2 = ${part1(input, 75)}")
//    println("Part 2 = ${part2(input)}")
}
