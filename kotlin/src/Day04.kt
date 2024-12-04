fun getCoordinatesAround(current: Coordinate, input: Map<Coordinate, Char>): Map<Coordinate, Char> {
    val coordinatesToSearch = listOf(
        -1 to 1,
        -1 to 0,
        -1 to -1,

        0 to -1,
        0 to 1,

        1 to -1,
        1 to 0,
        1 to 1,
    )
    val map = mutableMapOf<Coordinate, Char>()
    for (coordinateToSearch in coordinatesToSearch) {
        val coordinate = Coordinate(current.column + coordinateToSearch.first, current.row + coordinateToSearch.second)
        val possibleChar = input[coordinate]
        if (possibleChar != null) {
            map[coordinate] = possibleChar
        }
    }
    return map.toMap()
}

var countedCoordinates = mutableSetOf<Coordinate>()
fun getWordCount(
    currentCoordinate: Coordinate,
    lettersLeft: String,
    input: Map<Coordinate, Char>,
    checkedCoordinates: Set<Coordinate>
): Int {
    return getCoordinatesAround(currentCoordinate, input)
        .filter { !checkedCoordinates.contains(it.key) }
        .filter { input[it.key] == lettersLeft.first() }
        .map { coordinate ->
            if (lettersLeft.length == 1) {
                if (countedCoordinates.contains(coordinate.key)) {
                    return@map 0
                }
                countedCoordinates.addAll(checkedCoordinates)
                countedCoordinates.add(coordinate.key)
                return@map 1
            }
            return@map getWordCount(coordinate.key, lettersLeft.drop(1), input, checkedCoordinates.plus(coordinate.key))
        }
        .sum()
}

fun main() {
    fun part1(inputt: Map<Coordinate, Char>): Int {
        var wordsFound = 0

        for (coordinate in inputt) {
            if (coordinate.value != 'X') {
                continue;
            }
            wordsFound += getWordCount(
                coordinate.key,
                "MAS",
                inputt,
                setOf(coordinate.key)
            )
        }

        var currentLine = 0
        for (mutableEntry in inputt.toSortedMap()) {
            if (currentLine != mutableEntry.key.row) {
                print("\n")
                currentLine++
            }
        }

        return wordsFound
    }

    fun part2(inputt: String): Int {
        return inputt.length
    }

    // Test if implementation meets criteria from the description, like:
    check(part1(readInputGridMap("Day04_example")) == 18)

    // Read the input from the `src/Day01.txt` file.
    val input = readInputGridMap("Day04")
    println("Part 1 = ${part1(input)}")
}
