fun checkPlot(
    input: Map<Coordinate, Char>,
    startPosition: Coordinate,
    alreadySeen: Set<Coordinate>
): Set<Coordinate> {
    val seen = alreadySeen.toMutableSet()
    val queue = mutableListOf(startPosition)
    val validPositions = mutableSetOf(startPosition)
    val letter = input[startPosition]!!

    while (queue.isNotEmpty()) {
        val positionToCheck = queue.removeFirst()
        seen.add(positionToCheck)
        val lettersAround =
            getCoordinatesAround(input, positionToCheck)
                .filter {
                    input[it] == letter &&
                            it !in seen &&
                            it !in queue
                }
        // Seen it all (for this plot anyway)
        if (lettersAround.isEmpty()) {
            continue
        }
        queue.addAll(lettersAround)
        validPositions.addAll(lettersAround)
    }

    return validPositions
}


fun main() {
    fun part1(input: Map<Coordinate, Char>): Int {
        val seen = mutableSetOf<Coordinate>()
        val plots = mutableMapOf<Set<Coordinate>, Char>()

        for (position in input.keys.sorted()) {
            if (position in seen) {
                continue
            }
            seen.add(position)
            val plot = checkPlot(input, position, seen)
            plots[plot] = input[position]!!
            seen.addAll(plot)
        }

        return plots.keys
            .map { getArea(it) * getPerimeter(it) }
            .sum()
    }
    fun part2(input: Map<Coordinate, Char>): Int {
        val seen = mutableSetOf<Coordinate>()
        val plots = mutableMapOf<Set<Coordinate>, Char>()

        for (position in input.keys.sorted()) {
            if (position in seen) {
                continue
            }
            seen.add(position)
            val plot = checkPlot(input, position, seen)
            plots[plot] = input[position]!!
            seen.addAll(plot)
        }

        return plots.keys
            .map { getArea(it) * getNumberOfCorners(it) }
            .sum()
    }

    // Test if implementation meets criteria from the description, like:
    check(part1(readInputGridMap("Day12_example")) == 140)
    check(part2(readInputGridMap("Day12_example")) == 80)
//    check(part2(readInput("Day11_example")) == 6)

    // Read the input from the `src/Day01.txt` file.
    val input = readInputGridMap("Day12")
    println("Part 1 = ${part1(input)}")
    println("Part 2 = ${part2(input)}")
//    println("Part 2 = ${part2(input)}")
}