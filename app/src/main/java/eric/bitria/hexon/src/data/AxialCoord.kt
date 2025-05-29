package eric.bitria.hexon.src.data

// Coordinate System
data class AxialCoord(
    internal val q: Int,
    internal val r: Int
) : Comparable<AxialCoord> {

    override fun toString(): String {
        return "($q, $r)"
    }

    override fun compareTo(other: AxialCoord): Int {
        return compareValuesBy(this, other, { it.q }, { it.r })
    }

    fun getNeighbor(dir: Direction) = when(dir) {
        Direction.NORTHEAST -> AxialCoord(q + 1, r - 1)
        Direction.EAST -> AxialCoord(q + 1, r)
        Direction.SOUTHEAST -> AxialCoord(q, r + 1)
        Direction.SOUTHWEST -> AxialCoord(q - 1, r + 1)
        Direction.WEST -> AxialCoord(q - 1, r)
        Direction.NORTHWEST -> AxialCoord(q, r - 1)
    }

    fun directionTo(other: AxialCoord): Direction {
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
}