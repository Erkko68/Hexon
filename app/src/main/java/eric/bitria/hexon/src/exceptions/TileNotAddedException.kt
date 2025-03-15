package eric.bitria.hexon.src.exceptions

import eric.bitria.hexon.src.data.Coord
import eric.bitria.hexon.src.data.Direction

class TileNotAddedException(coord: Coord, direction: Direction) : IllegalStateException(
    "Tile at $coord has not been added to the board. Missing element for direction $direction."
)
