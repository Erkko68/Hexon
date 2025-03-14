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

    fun getS(): Int{ return -q-r };
}