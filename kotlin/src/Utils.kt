import java.math.BigInteger
import java.security.MessageDigest
import kotlin.io.path.Path
import kotlin.io.path.readText

data class GridPosition(
    val column: Int,
    val row: Int,
    val character: Char,
) {}

/**
 * Reads lines from the given input txt file.
 */
fun readInput(name: String) = Path("kotlin/src/$name.txt").readText().trim()

fun readInputLines(name: String) = readInput(name).lines()

fun readInputGrid(name: String): Set<GridPosition> {
    val lines = readInputLines(name)
    return lines.foldIndexed(
        setOf(),
        operation = { lineIndex, acc, line -> acc.plus(line.mapIndexed { charIndex, c -> GridPosition(charIndex, lineIndex, c) }) }
    )
}
fun readInputGridMap(name: String): Map<String, GridPosition> {
    val lines = readInputLines(name)
    val map = mutableMapOf<String, GridPosition>()

    lines.forEachIndexed { lineIndex, line ->
        line.forEachIndexed { charIndex, char ->
            map["${lineIndex}-${charIndex}"] = GridPosition(charIndex, lineIndex, char)
        }
    }
    return map.toMap()
}

/**
 * The cleaner shorthand for printing output.
 */
fun Any?.println() = println(this)
