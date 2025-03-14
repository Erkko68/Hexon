package eric.bitria.hexon.src.data

// Coordinate System
data class Coord(val q: Int, val r: Int) : Comparable<Coord> {
    override fun compareTo(other: Coord): Int {
        return compareValuesBy(this, other, { it.q }, { it.r })
    }

    fun getNeighbor(dir: Direction) = when(dir) {
        Direction.NORTHEAST -> Coord(q + 1, r - 1)
        Direction.EAST -> Coord(q + 1, r)
        Direction.SOUTHEAST -> Coord(q, r + 1)
        Direction.SOUTHWEST -> Coord(q - 1, r + 1)
        Direction.WEST -> Coord(q - 1, r)
        Direction.NORTHWEST -> Coord(q, r - 1)
    }

    fun getNeighborCoords(): List<Coord> {
        return Direction.entries.map { this.getNeighbor(it) }
    }

    fun directionTo(other: Coord): Direction {
        val dq = other.q - q
        val dr = other.r - r
        return when (dq to dr) {
            1 to 0 -> Direction.EAST
            0 to 1 -> Direction.SOUTHEAST
            -1 to 1 -> Direction.SOUTHWEST
            -1 to 0 -> Direction.WEST
            0 to -1 -> Direction.NORTHWEST
            1 to -1 -> Direction.NORTHEAST
            else -> throw IllegalArgumentException("Coordinates are not adjacent")
        }
    }

    private fun getS(): Int{ return -q-r }
}