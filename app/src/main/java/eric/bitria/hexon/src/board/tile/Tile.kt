package eric.bitria.hexon.src.board.tile

import eric.bitria.hexon.src.data.AxialCoord
import eric.bitria.hexon.src.data.Direction
import eric.bitria.hexon.src.data.game.Resource

class Tile(
    val coords: AxialCoord,
    val resource: Resource,
    val number: Int
) {
    internal val edges = mutableMapOf<Direction, Edge>()
    internal val vertices = mutableMapOf<Direction, Vertex>()

    /**
     * Gives the Tile resource to the existing Buildings
     */
    fun giveResource() {
        vertices.forEach{
            if(it.value.hasBuilding()){
                it.value.player?.addResource(resource,it.value.building.value)
            }
        }
    }
}
