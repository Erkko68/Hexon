package eric.bitria.hexon.src.board.tile

import eric.bitria.hexon.src.data.Building
import eric.bitria.hexon.src.data.Coord
import eric.bitria.hexon.src.exceptions.InvalidBuildingException

class Edge(
    val coords: Pair<Coord, Coord>
){
    private var building: Building = Building.NONE

    fun hasBuilding() = building != Building.NONE

    fun placeBuilding(building: Building) {
        if (building != Building.ROAD) throw InvalidBuildingException("An edge can only have a ROAD type building")
        this.building = building
    }
}