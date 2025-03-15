package eric.bitria.hexon.src.exceptions

import eric.bitria.hexon.src.data.AxialCoord
import eric.bitria.hexon.src.data.Direction

class TileNotAddedException(axialCoord: AxialCoord, direction: Direction) : IllegalStateException(
    "Tile at $axialCoord has not been added to the board. Missing element for direction $direction."
)
