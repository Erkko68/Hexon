package eric.bitria.hexon.src.board.tile

import eric.bitria.hexon.src.data.Building

class Edge{

    private var building: Building = Building.NONE

    fun hasBuilding() = building != Building.NONE

    fun placeBuilding(building: Building) {
        this.building = building
    }
}