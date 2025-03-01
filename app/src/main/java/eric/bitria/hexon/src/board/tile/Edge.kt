package eric.bitria.hexon.src.board.tile

import eric.bitria.hexon.src.data.Building
import eric.bitria.hexon.src.exceptions.InvalidBuildingException

class Edge {
    private var building: Building = Building.NONE

    var tiles: Array<Tile?> = arrayOfNulls(2) // Each edge can be shared by two tiles at most.

    fun placeBuilding(building: Building) {
        if (building != Building.ROAD) {
            throw InvalidBuildingException("Only roads can be placed on an edge.")
        }
        this.building = building
    }

    fun getBuilding(): Building {
        return this.building
    }
}
