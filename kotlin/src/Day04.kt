fun getDirections(): List<Coordinate> {
    val directions = listOf(
        -1 to 1,
        -1 to 0,
        -1 to -1,

        0 to -1,
        0 to 1,

        1 to 1,
        1 to 0,
        1 to -1,
    )
    return directions.map { Coordinate(it.first, it.second) }
}

fun getStringsToMatch(middle: Coordinate, input: Map<Coordinate, Char>): Set<Coordinate>? {
    val middleLetter = input[middle] ?: return null
    if (middleLetter != 'A') {
        return null
    }
    val topLeft = Coordinate(column = middle.column - 1, row = middle.row - 1)
    val bottomLeft = Coordinate(column = middle.column - 1, row = middle.row + 1)
    val topRight = Coordinate(column = middle.column + 1, row = middle.row - 1)
    val bottomRight = Coordinate(column = middle.column + 1, row = middle.row + 1)

    val letterTopLeft = input[topLeft] ?: return null
    val letterTopRight = input[topRight] ?: return null
    val letterBottomLeft = input[bottomLeft] ?: return null
    val letterBottomRight = input[bottomRight] ?: return null

    val coords = setOf(topLeft, topRight, bottomLeft, bottomRight, middle)
    val letters = listOf(letterTopRight, letterTopLeft, letterBottomLeft, letterBottomRight)

    val posM = coords.filter { input[it] == 'M' }

    if (letters.count { it == 'M' } != 2 || letters.count { it == 'S' } != 2) {
        return null
    }

    if (posM[0].row == posM[1].row || posM[0].column == posM[1].column) {
        return coords;
    }

    return null
}

var countedCoordinates = mutableSetOf<List<Coordinate>>()
fun getWordCount(
    lettersLeft: String,
    input: Map<Coordinate, Char>,
    direction: Coordinate,
    checkedCoordinates: List<Coordinate>,
): Int {
    val newCoordinate =
        Coordinate(checkedCoordinates.last().column + direction.column, checkedCoordinates.last().row + direction.row)
    val letterAtCoordinate = input[newCoordinate]
    if (newCoordinate !in input.keys) {
        return 0
    }
    if (lettersLeft.first() != letterAtCoordinate) {
        return 0;
    }
    if (countedCoordinates.contains(checkedCoordinates.sorted())) {
        return 0
    }
    if (lettersLeft.length == 1) {
        countedCoordinates.add(checkedCoordinates.plus(newCoordinate).sorted())
        return 1
    }
    return getWordCount(lettersLeft.drop(1), input, direction, checkedCoordinates.plus(newCoordinate))
}

fun main() {
    fun part1(inputt: Map<Coordinate, Char>): Int {
        var wordsFound = 0

        val directions = getDirections()
        for (coordinate in inputt) {
            if (coordinate.value == 'X') {
                wordsFound += directions.sumOf {
                    getWordCount(
                        "MAS",
                        inputt,
                        it,
                        listOf(coordinate.key),
                    )
                }
            } else if (coordinate.value == 'S') {
                wordsFound += directions.sumOf {
                    getWordCount(
                        "AMX",
                        inputt,
                        it,
                        listOf(coordinate.key),
                    )
                }
            }


        }

        return wordsFound / 2
    }

    fun part2(input: Map<Coordinate, Char>): Int {
        val crosses = mutableSetOf<Coordinate>()
        for (key in input.keys) {
            val middle = getStringsToMatch(key, input)
            if (middle != null) {
                crosses.addAll(middle)
            }
        }

        var currentRow = 0
        for (coordinate in input.keys.sorted()) {
            if (currentRow != coordinate.row) {
                print("\n")
                currentRow += 1
            }
            if (coordinate in crosses) {
                print(input[coordinate])
            } else {
                print(".")
            }
        }

        return crosses.count { input[it] == 'A' }
    }

    // Test if implementation meets criteria from the description, like:
    check(part1(readInputGridMap("Day04_example")) == 18)
    check(part2(readInputGridMap("Day04_example")) == 9)

    // Read the input from the `src/Day01.txt` file.
    val input = readInputGridMap("Day04")
    println("Part 1 = ${part1(input)}")
    println("Part 2 = ${part2(input)}")
}
