package eric.bitria.hexon.src.exceptions

import eric.bitria.hexon.src.data.AxialCoord

class InvalidBoardException : IllegalArgumentException {
    constructor(axialCoord: AxialCoord, radius: Int) :
            super("Coordinate $axialCoord is out of range for a board with radius $radius.")

    constructor(message: String) :
            super(message)
}
