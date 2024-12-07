val regex = Regex("(\\d+) (\\|\\||\\*|\\+) (\\d+)")

fun getCalibrationResult(line: String): Long {
    val split = line.split(":")
    val testValue = split[0].trim().toLong()
    val values = split[1].trim().split(" ")

    val possibilities = mutableSetOf(
        values[0],
    )
    for (valueIndex in values.indices.drop(1)) {
        val value = values[valueIndex]
        val toAdd = mutableSetOf<String>()
        for (possibility in possibilities) {
            toAdd.add("$possibility + $value")
            toAdd.add("$possibility * $value")
            toAdd.add("$possibility || $value")
        }
        possibilities.clear()
        possibilities.addAll(toAdd)
    }

    for (possibility in possibilities) {
        var newPossibility = possibility

        while (regex.containsMatchIn(newPossibility)) {
            val nextMatch = regex.find(newPossibility)
            val first = nextMatch!!.groups[1]!!.value
            val operator = nextMatch.groups[2]!!.value
            val second = nextMatch.groups[3]!!.value

            if (first.toLong() > testValue) {
                newPossibility = "0"
                break
            }

            val result = when (operator) {
                "||" -> first + second
                "+" ->  (first.toLong() + second.toLong()).toString()
                "*" -> (first.toLong() * second.toLong()).toString()
                else -> throw Error("Weird operator $operator")
            }

            newPossibility = newPossibility.replaceFirst(nextMatch.value, result)
        }

        if (testValue == newPossibility.toLong()) {
            return testValue
        }
    }

    return 0
}

fun main() {
    fun part2(input: List<String>): Long {
        val results = input.map { getCalibrationResult(it) }
        return results.sum()
    }

    // Test if implementation meets criteria from the description, like:
//    check(part1(readInputLines("Day07_example")) == 3749L)
    check(part2(readInputLines("Day07_example")) == 11387L)
//    check(part2(readInputGridMap("Day07_example")) == 6)

    // Read the input from the `src/Day01.txt` file.
    val input = readInputLines("Day07")
//    println("Part 1 = ${part1(input)}")
    println("Part 2 = ${part2(input)}")
}
