
fun getGuardCurrentCoordinate(input: Map<Coordinate, Char>): Pair<Coordinate?, Direction?>  {
    val guard = input.filter { listOf('^', 'v', '<', '>').contains(it.value) }
    val directionGuard = getDirectionByCharacter(guard.values.first())

    if (guard.isEmpty()) {
        return Pair(null, null)
    }
    assert(guard.size == 1, { "More than 1 guard found" })

    return Pair(guard, directionGuard)
}

fun getGuardNextCoordinate(input: Map<Coordinate, Char>): Pair<Coordinate?, Direction?> {
    val (guard, direction) = getGuardCurrentCoordinate(input)
    val nextCoordinate = when (directionGuard) {
        Direction.Right -> Coordinate(guard.keys.first().column + 1, guard.keys.first().row)
        Direction.Left -> Coordinate(guard.keys.first().column - 1, guard.keys.first().row)
        Direction.Up -> Coordinate(guard.keys.first().column, guard.keys.first().row - 1)
        Direction.Down -> Coordinate(guard.keys.first().column, guard.keys.first().row + 1)
    }

    return Pair(nextCoordinate, directionGuard)
}

fun nextGuardTick(input: Map<Coordinate, Char>): Pair<Coordinate, Direction>? {
    val obstacles = input.filter { it.value == '#' }.keys
    val (nextCoordinate, directionGuard) = getGuardNextCoordinate(input)

    if (nextCoordinate == null || directionGuard == null) {
        return null
    }

    if (nextCoordinate !in obstacles) {
        return Pair(Coordinate(coordinateGuard.column, coordinateGuard.row), directionGuard)
    }
    return Pair(nextCoordinate, turnRightFrom(directionGuard))
}

fun main() {
    fun part1(input: Map<Coordinate, Char>): Int {
        val positions = mutableSetOf<Coordinate>()
        val currentMap = input.toMutableMap()
        val guardTick =
        while (guardTick != null) {
            currentMap
            guardTick = nextGuardTick(input)
        }

        return 0;
    }

    fun part2(input: List<String>): Int {
        return 0
    }

    // Test if implementation meets criteria from the description, like:
    check(part1(readInputGridMap("Day06_example")) == 41)
//    check(part2(readInputLines("Day06_example")) == 123)

    // Read the input from the `src/Day01.txt` file.
    val input = readInputGridMap("Day06")
    println("Part 1 = ${part1(input)}")
//    println("Part 2 = ${part2(input)}")
}
