import kotlin.math.ceil

/**
 * Return ths middle of found, otherwise null
 */
fun checkUpdateValid(update: List<Int>, rules: List<Pair<Int, Int>>): Int? {
    if (update.size == 1) {
        return update.first()
    }
    for (rule in rules) {
        val applicableUpdate = update.filter { it == rule.first || it == rule.second }
        if (applicableUpdate.isEmpty()) {
            continue
        }
        if (!applicableUpdate.contains(rule.first) || !applicableUpdate.contains(rule.second)) {
            continue
        }
        val firstIndex = applicableUpdate.lastIndexOf(rule.first)
        val secondIndex = applicableUpdate.indexOf(rule.second)
        if (firstIndex > secondIndex) {
            return null;
        }
    }
    return update[ceil((update.size / 2).toDouble()).toInt()]
}

fun reorderUpdate(update: List<Int>, rules: List<Pair<Int, Int>>): Int? {
    if (checkUpdateValid(update, rules) != null) {
        return null;
    }
    val mutableUpdate = update.toMutableList()
    var ruleApplied = false;
    do {
        ruleApplied = false;
        for (rule in rules) {
            val firstIndex = mutableUpdate.indexOf(rule.first)
            val secondIndex = mutableUpdate.indexOf(rule.second)
            if (firstIndex == -1 || secondIndex == -1) {
                continue
            }
            if (firstIndex < secondIndex) {
                continue
            }
            ruleApplied = true;
            val firstValue = mutableUpdate[firstIndex]
            val secondValue = mutableUpdate[secondIndex]

            mutableUpdate[firstIndex] = secondValue
            mutableUpdate[secondIndex] = firstValue
        }
    } while (ruleApplied)

    return mutableUpdate[ceil((mutableUpdate.size / 2).toDouble()).toInt()]
}

fun main() {
    fun part1(input: List<String>): Int {
        val blankLine = input.indexOfFirst { it.isBlank() }
        val orderRules = input.take(blankLine)
            .map {
                val split = it.split('|')
                return@map split[0].toInt() to split[1].toInt()
            }
        val updateSum = input.drop(blankLine + 1)
            .map { update ->
                update.split(',').map { it.toInt() }
            }
            .mapNotNull { checkUpdateValid(it, orderRules) }
            .sum()

        return updateSum;
    }

    fun part2(input: List<String>): Int {
        val blankLine = input.indexOfFirst { it.isBlank() }
        val orderRules = input.take(blankLine)
            .map {
                val split = it.split('|')
                return@map split[0].toInt() to split[1].toInt()
            }
        val updateSum = input.drop(blankLine + 1)
            .map { update ->
                update.split(',').map { it.toInt() }
            }
            .mapNotNull { reorderUpdate(it, orderRules) }
            .sum()

        return updateSum;
    }

    // Test if implementation meets criteria from the description, like:
    check(part1(readInputLines("Day05_example")) == 143)
    check(part2(readInputLines("Day05_example")) == 123)

    // Read the input from the `src/Day01.txt` file.
    val input = readInputLines("Day05")
    println("Part 1 = ${part1(input)}")
    println("Part 2 = ${part2(input)}")
}
