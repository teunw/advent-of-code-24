import kotlin.io.path.Path
import kotlin.io.path.readText

enum class Direction {
    Up,
    Down,
    Left,
    Right
}

val charToDirection = hashMapOf(
    'v' to Direction.Down,
    '^' to Direction.Up,
    '>' to Direction.Right,
    '<' to Direction.Left,
)

fun getDirectionByCharacter(char: Char): Direction {
    return charToDirection[char] ?: throw Error("Kaput")
}

fun getCharacterByDirection(dir: Direction): Char {
    return charToDirection.entries.first { it.value == dir }.key
}

fun turnRightFrom(direction: Direction): Direction {
    return when (direction) {
        Direction.Right -> Direction.Down
        Direction.Down -> Direction.Left
        Direction.Left -> Direction.Up
        Direction.Up -> Direction.Right
    }
}

fun getNextCoordinate(c: Coordinate, direction: Direction): Coordinate {
    return when (direction) {
        Direction.Right -> Coordinate(c.column + 1, c.row)
        Direction.Down -> Coordinate(c.column, c.row + 1)
        Direction.Left -> Coordinate(c.column - 1, c.row)
        Direction.Up -> Coordinate(c.column, c.row - 1)
    }
}

fun getArea(coordinates: Set<Coordinate>): Int {
    return coordinates.size
}

fun getPerimeter(coordinates: Set<Coordinate>): Int {
    return coordinates.map {
        val around = getCoordinatesAround(coordinates, it)
        return@map (4 - around.size)
    }.sum()
}

fun goInDirection(coordinates: Set<Coordinate>, coordinate: Coordinate, direction: Coordinate) {

}

fun getNumberOfCorners(coordinates: Set<Coordinate>): Int {
    if (coordinates.size == 1) {
        return 4
    }

    val distinctColumns = coordinates.map { it.column }.distinct()
    val distinctRows = coordinates.map { it.row }.distinct()
    val edges = coordinates.filter {
        getCoordinatesAround(coordinates, it).size < 4
    }.toMutableSet()

    val positionsToCheck = mutableListOf(edges.first())
    val positionsChecked = mutableSetOf<Coordinate>()
    var direction: Direction? = null
    while (positionsToCheck.isNotEmpty()) {
        val nextPosition = positionsToCheck.removeFirst()
        positionsChecked.add(nextPosition)

        val coordinateTop = Coordinate(column = nextPosition.column, row = nextPosition.row - 1)
        val coordinateBottom = Coordinate(column = nextPosition.column, row = nextPosition.row + 1)
        val coordinateRight = Coordinate(column = nextPosition.column + 1, row = nextPosition.row)
        val coordinateLeft = Coordinate(column = nextPosition.column - 1, row = nextPosition.row)

        if (direction == Direction.Down) {
            if (coordinateTop in edges) {
                positionsToCheck.add(coordinateTop)
            }
            if (coordinateBottom in edges) {
                positionsToCheck.add(coordinateTop)
            }
            continue
        }
        if (direction == null) {
            if (coordinateTop in edges && coordinateBottom in edges) {
                direction = Direction.Down
            }
            if (coordinateLeft in edges && coordinateRight in edges) {
                direction = Direction.Left
            }
        }
    }

    if (distinctRows.size == 1 || distinctColumns.size == 1) {
        return corners + 2
    }

    return corners
}

fun <T> getCoordinatesAround(map: Map<Coordinate, T>, c: Coordinate): Set<Coordinate> {
    return setOf(
        Coordinate(column = c.column - 1, row = c.row),
        Coordinate(column = c.column + 1, row = c.row),
        Coordinate(column = c.column, row = c.row + 1),
        Coordinate(column = c.column, row = c.row - 1),
    ).filter { map.keys.contains(it) }.toSet()
}

fun getCoordinatesAround(map: Set<Coordinate>, c: Coordinate): Set<Coordinate> {
    return setOf(
        Coordinate(column = c.column - 1, row = c.row),
        Coordinate(column = c.column + 1, row = c.row),
        Coordinate(column = c.column, row = c.row + 1),
        Coordinate(column = c.column, row = c.row - 1),
    ).filter { it in map }.toSet()
}

data class Coordinate(
    val column: Int,
    val row: Int,
) : Comparable<Coordinate> {

    operator fun plus(other: Coordinate): Coordinate = Coordinate(column + other.column, row + other.row)
    operator fun minus(other: Coordinate): Coordinate = Coordinate(column - other.column, row - other.row)

    override fun compareTo(other: Coordinate): Int {
        val rowDiff = row - other.row
        if (rowDiff != 0) {
            return rowDiff
        }
        val columnDiff = column - other.column
        if (columnDiff != 0) {
            return columnDiff
        }
        return 0
    }

    override fun equals(other: Any?): Boolean {
        return other is Coordinate &&
                other.row == row &&
                other.column == column
    }

    override fun hashCode(): Int {
        return javaClass.hashCode()
    }
}

data class CoordinateWithChar(
    val column: Int,
    val row: Int,
    val character: Char,
)

fun <T : Comparable<T>> cartesianProduct(list: List<T>): List<Pair<T, T>> =
    list.fold(listOf()) { acc, thing ->
        acc.plus(list.except(thing).map {
            Pair(it, thing)
        })
    }

fun <T : Comparable<T>> cartesianProductUnique(list: List<T>): Set<Pair<T, T>> =
    list.fold(listOf<Pair<T, T>>(), { acc, thing ->
        acc.plus(list.except(thing).map {
            Pair(it, thing)
        })
    }).toSet()

/**
 * Reads lines from the given input txt file.
 */
fun readInput(name: String) = Path("kotlin/src/$name.txt").readText().trim()

fun readInputLines(name: String) = readInput(name).lines()

fun readInputGrid(name: String): Set<CoordinateWithChar> {
    val lines = readInputLines(name)
    return lines.foldIndexed(
        setOf(),
        operation = { lineIndex, acc, line ->
            acc.plus(line.mapIndexed { charIndex, c ->
                CoordinateWithChar(
                    charIndex,
                    lineIndex,
                    c
                )
            })
        }
    )
}

fun readInputGridMap(name: String): Map<Coordinate, Char> {
    val lines = readInputLines(name)
    val map = mutableMapOf<Coordinate, Char>()

    lines.forEachIndexed { lineIndex, line ->
        line.forEachIndexed { charIndex, char ->
            map[Coordinate(charIndex, lineIndex)] = char
        }
    }
    return map.toMap()
}

fun readInputGridMapMulti(name: String): Map<Coordinate, Set<Char>> {
    val lines = readInputLines(name)
    val map = mutableMapOf<Coordinate, Set<Char>>()

    lines.forEachIndexed { lineIndex, line ->
        line.forEachIndexed { charIndex, char ->
            map[Coordinate(charIndex, lineIndex)] = setOf(char)
        }
    }
    return map.toMap()
}

fun <T> List<T>.except(thing: T): List<T> {
    return this.filterNot { it == thing }
}

fun Map<Coordinate, Char>.draw() {
    val row = this.minOf { it.key.row }
    val toRow = this.maxOf { it.key.row }
    val column = this.minOf { it.key.column }
    val toColumn = this.maxOf { it.key.column }
    for (r in row..toRow) {
        for (c in column..toColumn) {
            print(this[Coordinate(c, r)])
        }
        print("\n")
    }
}

fun Map<Coordinate, Set<Char>>.drawSet(except: Set<Char> = setOf()) {
    val row = this.minOf { it.key.row }
    val toRow = this.maxOf { it.key.row }
    val column = this.minOf { it.key.column }
    val toColumn = this.maxOf { it.key.column }
    val maxLength = this.maxOf { it.value.minus(except).size }
    for (r in row..toRow) {
        for (c in column..toColumn) {
            print(
                "(${
                    this[Coordinate(c, r)]!!.filterNot { except.contains(it) }.joinToString("").padEnd(maxLength, '.')
                })"
            )
        }
        print("\n")
    }
}