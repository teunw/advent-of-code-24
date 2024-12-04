fun getCoordinatesAround(position: GridPosition, input: Map<String, GridPosition>): Set<GridPosition> {
    val coordinatesToSearch = listOf(
        1 to 1,
        -1 to -1,
        0 to 1,
        0 to -1,
        1 to -1,
        -1 to 1,
        1 to 0,
        -1 to 0,
    )
    return coordinatesToSearch.mapNotNull { mappingCoord -> input["${mappingCoord.first}-${mappingCoord.second}"] }.toSet()
}

fun main() {
    fun part1(inputt: Map<String, GridPosition>): Int {
        var wordsFound = 0
        for (gridPosition in inputt) {
            if (gridPosition.value.character != 'X') {
                continue
            }

            // Already found X
            val wordToSearch = "MAS"
            var currentIndex = 0
            var nextCoordinates = getCoordinatesAround(gridPosition.value, inputt).toMutableList()

            while (nextCoordinates.isNotEmpty()) {
                val coordinateToSearch = nextCoordinates.removeLast()
                val coordinateColumn = coordinateToSearch.column
                val coordinateRow = coordinateToSearch.row
                val possibleCharacter = inputt.find { it.column == coordinateColumn && it.row == coordinateRow }
                if (possibleCharacter == null) {
                    continue;
                }
                if (possibleCharacter.character == wordToSearch[currentIndex]) {
                    // Found a letter
                    currentIndex += 1
                    nextCoordinates.add(possibleCharacter)
                }
            }
            if (currentIndex == wordToSearch.length - 1) {
                // Found the whole word
                wordsFound += 1
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
    val input = readInputGrid("Day04")
    println("Part 1 = ${part1(input)}")
}
