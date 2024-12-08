fun createNodesForPart1(antennaType: Char, input: Map<Coordinate, Set<Char>>): Map<Coordinate, Set<Char>> {
    val antennas = input.entries
        .filter { it.value.contains(antennaType) }
    val mutableInput = input.toMutableMap()
    val certesian = cartesianProductUnique(antennas.map { it.key })

    val antiNodes = mutableSetOf<Coordinate>()
    for (product in certesian) {
        val difference = product.first - product.second

        val antiNodeAt = product.first + difference
        val antiNodeAt4 = product.second - difference

        antiNodes.add(antiNodeAt)
        antiNodes.add(antiNodeAt4)
    }

    for (antiNode in antiNodes) {
        if (!mutableInput.keys.contains(antiNode)) {
            continue
        }
        mutableInput[antiNode] = mutableInput[antiNode]!!.plus('#')
    }

    return mutableInput.toMap()
}

fun createNodesForPart2(antennaType: Char, input: Map<Coordinate, Set<Char>>): Map<Coordinate, Set<Char>> {
    val antennas = input.entries
        .filter { it.value.contains(antennaType) }
    val mutableInput = input.toMutableMap()
    val certesian = cartesianProductUnique(antennas.map { it.key })

    val antiNodes = mutableSetOf<Coordinate>()
    for (product in certesian) {
        val difference = product.first - product.second

        var antiNodeFirst = product.first + difference
        while (antiNodeFirst in mutableInput.keys) {
            antiNodes.add(antiNodeFirst)
            antiNodeFirst += difference
        }
        var antiNodeSecond = product.second - difference
        while (antiNodeSecond in mutableInput.keys) {
            antiNodes.add(antiNodeSecond)
            antiNodeSecond += difference
        }
    }

    for (antiNode in antiNodes) {
        if (!mutableInput.keys.contains(antiNode)) {
            continue
        }
        mutableInput[antiNode] = mutableInput[antiNode]!!.plus('#')
    }

    return mutableInput.toMap()
}

fun main() {
    fun part1(input: Map<Coordinate, Set<Char>>): Int {
        val antennaTypes = input.values.flatten()
            .toSet()
            .filter { it != '.' }
        var inputWithNodes = input
        for (antennaType in antennaTypes) {
            inputWithNodes = createNodesForPart1(antennaType, inputWithNodes)
        }
        val count = inputWithNodes.count { it.value.contains('#') }
        return count
    }

    fun part2(input: Map<Coordinate, Set<Char>>): Int {
        val antennaTypes = input.values.flatten()
            .toSet()
            .filter { it != '.' }
        var inputWithNodes = input
        for (antennaType in antennaTypes) {
            inputWithNodes = createNodesForPart2(antennaType, inputWithNodes)
        }
        inputWithNodes.drawSet()
        val count = inputWithNodes.count { it.value.contains('#') }
        return count
    }

    // Test if implementation meets criteria from the description, like:
    check(part1(readInputGridMapMulti("Day08_example")) == 14)
    check(part2(readInputGridMapMulti("Day08_example")) == 34)

    // Read the input from the `src/Day01.txt` file.
    val input = readInputGridMapMulti("Day08")
    println("Part 1 = ${part1(input)}")
    println("Part 2 = ${part2(input)}")
}
