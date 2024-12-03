fun main() {
    fun part1(inputt: String): Int {
        val mulRegex = Regex(pattern = "mul\\(([0-9]{1,3}),([0-9]{1,3})\\)")

        val result = mulRegex.findAll(inputt)
            .map { listOf(it.groupValues[1].toInt(), it.groupValues[2].toInt()) }
            .map { it[0] * it[1] }
            .sum()

        return result
    }

    fun part2(inputt: String): Int {
        val mulRegex = Regex(pattern = "mul\\(([0-9]{1,3}),([0-9]{1,3})\\)|do\\(\\)|don\'t\\(\\)")

        val values = mutableListOf<Int>()
        val instructions = mulRegex.findAll(inputt)
        var enabled = true
        for (instr in instructions) {
            if (instr.groupValues[0] == "do()") {
                enabled = true
                continue
            }
            if (instr.groupValues[0] == "don't()") {
                enabled = false
                continue
            }
            if (enabled) {
                values.add(instr.groupValues[1].toInt() * instr.groupValues[2].toInt())
            }
        }

        return values.sum()
    }

    // Test if implementation meets criteria from the description, like:
    check(part1("xmul(2,4)%&mul[3,7]!@^do_not_mul(5,5)+mul(32,64]then(mul(11,8)mul(8,5))") == 161)
    check(part2("xmul(2,4)&mul[3,7]!^don't()_mul(5,5)+mul(32,64](mul(11,8)undo()?mul(8,5))") == 48)

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day03")
    println("Part 1 = ${part1(input)}")
    println("Part 2 = ${part2(input)}")
}
