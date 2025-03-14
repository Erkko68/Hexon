package eric.bitria.hexon.src.data

enum class Direction {
    NORTHEAST,
    EAST,
    SOUTHEAST,
    SOUTHWEST,
    WEST,
    NORTHWEST;

    fun next(): Direction = entries[(ordinal + 1) % 6]
    fun prev(): Direction = entries[(ordinal + 5) % 6]
    fun opposite(): Direction = entries[(ordinal + 3) % 6]
}