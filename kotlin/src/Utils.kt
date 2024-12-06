import kotlin.io.path.Path
import kotlin.io.path.readText

enum class Direction {
    Up,
    Down,
    Left,
    Right
}

fun getDirectionByCharacter(char: Char): Direction {
    return when (char.lowercaseChar()) {
        'v' -> Direction.Down
        '^' -> Direction.Up
        '>' -> Direction.Right
        '<' -> Direction.Left
        else -> throw Error("Invalid character '$char'")
    }
}

fun turnRightFrom(direction: Direction): Direction {
    return when (direction) {
        Direction.Right -> Direction.Down
        Direction.Down -> Direction.Left
        Direction.Left -> Direction.Up
        Direction.Up -> Direction.Right
    }
}

data class Coordinate(
    val column: Int,
    val row: Int,
) : Comparable<Coordinate> {
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
}

data class CoordinateWithChar(
    val column: Int,
    val row: Int,
    val character: Char,
)

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

/**
 * The cleaner shorthand for printing output.
 */
fun Any?.println() = println(this)
