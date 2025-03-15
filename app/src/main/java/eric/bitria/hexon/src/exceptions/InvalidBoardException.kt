package eric.bitria.hexon.src.exceptions

import eric.bitria.hexon.src.data.Coord

class InvalidBoardException : IllegalArgumentException {
    constructor(coord: Coord, radius: Int) :
            super("Coordinate $coord is out of range for a board with radius $radius.")

    constructor(message: String) :
            super(message)
}
