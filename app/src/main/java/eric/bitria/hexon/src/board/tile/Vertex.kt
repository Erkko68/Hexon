package eric.bitria.hexon.src.board.tile

import eric.bitria.hexon.src.data.BuildingType

// Vertex Holds the building value
data class Vertex(
    var building: BuildingType = BuildingType.NONE
)