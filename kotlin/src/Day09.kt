fun isFile(index: Int): Boolean {
    if (index == 0) {
        return true
    }
    if (index % 2 == 0) {
        return true
    }
    return false
}

fun createChecksum(disk: List<DiskData>): ULong {
    return disk.foldIndexed(0UL) { index, acc, diskData ->
        if (diskData.id == null) {
            return@foldIndexed acc
        }
        return@foldIndexed acc + (diskData.id * index).toULong()
    }
}

data class DiskData(val id: Int?)
data class DiskDataBlock(val id: Int?, val size: Int)

fun main() {
    fun part1(input: String): ULong {
        var currentId = 0
        val disk = input.toCharArray().foldIndexed(listOf<DiskData>()) { index, acc, it ->
            val digit = it.digitToInt()
            if (isFile(index)) {
                val result = acc.plus(Array(digit) { DiskData(currentId) })
                currentId++;
                return@foldIndexed result
            }
            return@foldIndexed acc.plus(Array(digit) { DiskData(null) })
        }.toMutableList()
        for (block in disk.indices.reversed()) {
            val current = disk[block]
            if (current.id == null) {
                continue
            }
            val firstFittingSpot = disk.indexOfFirst { it.id == null }
            if (block < firstFittingSpot) {
                break
            }
            disk[block] = disk[firstFittingSpot]
            disk[firstFittingSpot] = current
        }
        return createChecksum(disk)
    }

    fun part2(input: String): ULong {
        val a = listOf(1,2,3,4,5,6,7).replace(4, 10)

        var currentId = 0
        val emptySpaceMap = mutableListOf<Pair<Int, Int>>()
        var disk = input.toCharArray().foldIndexed(listOf<DiskDataBlock>()) { index, acc, it ->
            val digit = it.digitToInt()
            if (isFile(index)) {
                val result = acc.plus(DiskDataBlock(currentId, digit))
                currentId++
                return@foldIndexed result
            }
            emptySpaceMap.add(Pair(index, digit))
            return@foldIndexed acc.plus(DiskDataBlock(null, digit))
        }.toMutableList()
        val ids = disk.mapNotNull { it.id }.sortedDescending()
        for (id in ids) {
            val index = disk.indexOfFirst { it.id == id }
            val current = disk[index]

            val spot = disk.indexOfFirst { it.id == null && it.size >= current.size }
            if (spot == -1) {
                continue
            }
            if (spot > index) {
                continue
            }
            val fittingSpot = disk[spot]
            val isEmpty = fittingSpot.size - current.size <= 0
            if (isEmpty) {
                disk[spot] = DiskDataBlock(id = current.id, size = current.size)
                disk[index] = DiskDataBlock(id = null, size = current.size)
            } else {
                val nullBlock = DiskDataBlock(id = null, size = fittingSpot.size - current.size)
                disk = disk
                    .take(spot)
                    .plus(current)
                    .plus(nullBlock)
                    // The rest of the disk
                    .plus(disk.drop(spot + 1).replace(current, DiskDataBlock(null, current.size)))
                    .toMutableList()
            }
        }
        val unpackedDisk = disk.fold(listOf<DiskData>()) { acc, part ->
            acc.plus(Array(part.size) { DiskData(part.id) })
        }
        return createChecksum(unpackedDisk)
    }

    // Test if implementation meets criteria from the description, like:
    check(part1(readInput("Day09_example")) == 1928UL)
    check(part2(readInput("Day09_example")) == 2858UL)

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day09")
    println("Part 1 = ${part1(input)}")
    println("Part 2 = ${part2(input)}")
}

private fun <E> List<E>.replace(element: E, with: E): List<E> {
    val index = this.indexOf(element)
    return this.take(index) + with + this.drop(index + 1)
}
