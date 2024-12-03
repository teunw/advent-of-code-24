import java.math.BigInteger
import java.security.MessageDigest
import kotlin.io.path.Path
import kotlin.io.path.readText

/**
 * Reads lines from the given input txt file.
 */
fun readInput(name: String) = Path("kotlin/src/$name.txt").readText().trim()

fun readInputLines(name: String) = readInput(name).lines()

/**
 * The cleaner shorthand for printing output.
 */
fun Any?.println() = println(this)
