package eric.bitria.hexon.src.board.tile

import eric.bitria.hexon.src.data.Building
import eric.bitria.hexon.src.exceptions.InvalidBuildingException

class Edge {
    private var building: Building = Building.NONE

    var vertex: Array<Vertex?> = arrayOfNulls(2) // Each edge is connected to two tiles at most.
    var tiles: Array<Tile?> = arrayOfNulls(2) // Each edge can be shared by two tiles at most.

    fun placeRoad() {
        this.building = Building.ROAD
    }

    fun canPlaceRoad(): Boolean {
        return !hasRoad()
    }

    private fun hasRoad(): Boolean {
        return this.building == Building.ROAD
    }
}
