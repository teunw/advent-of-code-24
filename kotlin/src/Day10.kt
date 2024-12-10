fun navigateTrailhead(input: Map<Coordinate, Int>, trailHead: Coordinate): Int {
    val toNavigate = mutableListOf(trailHead)
    val navigated = mutableSetOf<Coordinate>()
    val highestTiles = mutableSetOf<Coordinate>()

    while (toNavigate.isNotEmpty()) {
        val pop = toNavigate.removeFirst()
        navigated.add(pop)

        val value = input[pop] ?: continue
        if (value == 9) {
            highestTiles.add(pop)
        }

        val tilesAround = getCoordinatesAround(input, pop)
            .filter { it !in navigated }
            .filter { input[it] == value + 1 }
        toNavigate.addAll(tilesAround)
    }
    return highestTiles.size
}

fun navigateTrailheadPart2(input: Map<Coordinate, Int>, trailHead: Coordinate): Int {
    val toNavigate = mutableListOf(
        setOf(trailHead),
    )
    val distinctTrails = mutableSetOf<Set<Coordinate>>()

    while (toNavigate.isNotEmpty()) {
        val pop = toNavigate.removeFirst()
        val lastCoordinate = pop.last()

        val value = input[lastCoordinate] ?: continue
        if (value == 9) {
            distinctTrails.add(pop)
            continue
        }

        val tilesAround = getCoordinatesAround(input, lastCoordinate)
            .filter { it !in pop }
            .filter { input[it] == value + 1 }
        toNavigate.addAll(
            tilesAround.map { pop.plus(it) }
        )
    }
    return distinctTrails.size
}

fun main() {
    fun part1(inputt: Map<Coordinate, Char>): Int {
        val mapDigit = inputt.entries
            .filterNot { it.value == '.' }
            .associate { it.key to it.value.digitToInt() }
        val sum = mapDigit
            .entries
            .filter { it.value == 0 }
            .map { navigateTrailhead(mapDigit, it.key) }
        return sum.sum()
    }

    fun part2(inputt: Map<Coordinate, Char>): Int {
        val mapDigit = inputt.entries
            .filterNot { it.value == '.' }
            .associate { it.key to it.value.digitToInt() }
        val sum = mapDigit
            .entries
            .filter { it.value == 0 }
            .map { navigateTrailheadPart2(mapDigit, it.key) }
        return sum.sum()
    }

    // Test if implementation meets criteria from the description, like:
    check(part1(readInputGridMap("Day10_example")) == 36)
    check(part2(readInputGridMap("Day10_example")) == 81)

    // Read the input from the `src/Day01.txt` file.
    val input = readInputGridMap("Day10")
    println("Part 1 = ${part1(input)}")
    println("Part 2 = ${part2(input)}")
}
