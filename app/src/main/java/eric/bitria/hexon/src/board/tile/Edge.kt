package eric.bitria.hexon.src.board.tile

import eric.bitria.hexon.src.data.BuildingType
import eric.bitria.hexon.src.exceptions.InvalidBuildingException

class Edge {
    var road: BuildingType = BuildingType.NONE

    var tiles: Array<Tile?> = arrayOfNulls(2) // Each edge can be shared by two tiles at most.

    fun placeRoad(building: BuildingType) {
        if (building != BuildingType.ROAD) {
            throw InvalidBuildingException("Only roads can be placed on an edge.")
        }
        this.road = building
    }
}
