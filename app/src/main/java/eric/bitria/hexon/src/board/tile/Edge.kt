package eric.bitria.hexon.src.board.tile

import eric.bitria.hexon.src.data.Building
import eric.bitria.hexon.src.data.Coord

class Edge(
    val coords: Pair<Coord, Coord>
){

    private var building: Building = Building.NONE

    fun hasBuilding() = building != Building.NONE

    fun placeBuilding(building: Building) {
        this.building = building
    }
}