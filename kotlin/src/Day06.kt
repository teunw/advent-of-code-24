val directions = setOf('^', 'v', '<', '>')

fun getGuardCurrentCoordinate(input: Map<Coordinate, Char>): Pair<Coordinate, Direction>?  {
    val guard = input.entries.firstOrNull { directions.contains(it.value) }
    if (guard == null) {
        return null
    }
    val directionGuard = getDirectionByCharacter(guard.value)

    return Pair(guard.key, directionGuard)
}

fun nextGuardTick(input: Map<Coordinate, Char>): Pair<Coordinate, Direction>? {
    val obstacles = input.filter { it.value == '#' }.keys
    val guard = getGuardCurrentCoordinate(input) ?: return null

    val next = getNextCoordinate(guard.first, guard.second)
    if (next in obstacles) {
        val nextDirection = turnRightFrom(guard.second)
        return Pair(guard.first, nextDirection)
    }
    if (next !in input.keys) {
        return null
    }
    return Pair(next, guard.second)
}

fun main() {
    fun part1(input: Map<Coordinate, Char>): Set<Coordinate> {
        val positions = mutableSetOf<Coordinate>()

        val currentMap = input.toMutableMap()
        val guard = getGuardCurrentCoordinate(currentMap)
        assert(guard != null)

        do {
            val currentGuard = getGuardCurrentCoordinate(currentMap) ?: break
            val guardTick = nextGuardTick(currentMap)
            currentMap[currentGuard.first] = '.'
            if (guardTick != null) {
                currentMap[guardTick.first] = getCharacterByDirection(guardTick.second)
                positions.add(guardTick.first)
            }

        } while (guardTick != null && positions.size < 10_000 /** just in case */)

        return positions
    }

    fun part2(input: Map<Coordinate, Char>): Int {
        val positions = part1(input)
        var yep = 0
        for (position in positions) {
            val newInput = input.toMutableMap()
            newInput[position] = '#'
            val newPositions = part1(newInput)
            if (newPositions.size > 10_000) {
                yep += 1
            }
        }
        return yep
    }

    // Test if implementation meets criteria from the description, like:
    check(part1(readInputGridMap("Day06_example")).size == 41)
    check(part2(readInputGridMap("Day06_example")) == 6)

    // Read the input from the `src/Day01.txt` file.
    val input = readInputGridMap("Day06")
    println("Part 1 = ${part1(input).size}")
//    println("Part 2 = ${part2(input)}")
}
