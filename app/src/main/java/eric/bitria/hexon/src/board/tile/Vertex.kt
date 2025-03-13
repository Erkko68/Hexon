package eric.bitria.hexon.src.board.tile

import eric.bitria.hexon.src.data.Building

// Vertex Holds the building value
class Vertex{
    var building: Building = Building.NONE
    var edges: Array<Edge?> = arrayOfNulls(3) // Each vertex can be shared by three edges at most.

    fun placeBuilding(building: Building) {
        this.building = building
    }

    /**
     * Checks if a building can be placed on this vertex.
     * A building cannot be placed if any opposite vertex already has one.
     *
     * @return `true` if a building can be placed, `false` otherwise.
     */
    fun canPlaceBuilding(): Boolean {
        return !hasBuilding() and getOppositeVertices().none { it.hasBuilding() }
    }

    /**
     * Retrieves the vertices opposite to this one along its connected edges.
     *
     * @return A list of opposite vertices.
     */
    private fun getOppositeVertices(): List<Vertex> {
        return edges.filterNotNull()
            .map { if (it.vertex[0] != this) it.vertex[0]!! else it.vertex[1]!! }
    }

    private fun hasBuilding():Boolean{
        return this.building != Building.NONE
    }

}