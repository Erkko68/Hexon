package eric.bitria.hexon.src.data.game

enum class Building(val recipe: Map<Resource, Int>, val value: Int) {
    NONE(emptyMap(),0),
    ROAD(mapOf(Resource.WOOD to 1, Resource.BRICK to 1),0),
    SETTLEMENT(mapOf(Resource.WOOD to 1, Resource.BRICK to 1, Resource.WHEAT to 1, Resource.SHEEP to 1),1),
    CITY(mapOf(Resource.WHEAT to 2, Resource.ORE to 3),2)
}