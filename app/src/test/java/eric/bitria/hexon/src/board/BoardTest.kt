package eric.bitria.hexon.src.board

import androidx.compose.ui.graphics.Color
import eric.bitria.hexon.src.board.tile.Tile
import eric.bitria.hexon.src.data.AxialCoord
import eric.bitria.hexon.src.data.Direction
import eric.bitria.hexon.src.data.game.Building
import eric.bitria.hexon.src.data.game.Resource
import eric.bitria.hexon.src.exceptions.InvalidBoardException
import eric.bitria.hexon.src.player.Player
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class BoardTest {

    private lateinit var board: Board
    private lateinit var player: Player

    @Before
    fun setUp() {
        board = Board(3)
        player = Player("Juan",Color.Red)
    }

    // AddTile

    @Test
    fun addTile_ShouldStoreTileAndCreateEdgesAndVertices() {
        val axialCoord = AxialCoord(0, 0)
        val tile = Tile(axialCoord, Resource.WOOD, 8)

        board.addTile(tile)

        // Verify the tile is stored
        assertEquals(tile, board.getTile(axialCoord))

        // Verify edges and vertices are created correctly
        Direction.entries.forEach { dir ->
            val neighborCoord = axialCoord.getNeighbor(dir)
            val edgeCoords = listOf(axialCoord, neighborCoord).sorted()
            val edgePair = Pair(edgeCoords[0], edgeCoords[1])

            assertNotNull("Edge should be created for direction $dir", board.getEdge(edgePair.first, edgePair.second))

            val neighborCoord1 = axialCoord.getNeighbor(dir.next())
            val vertexCoords = listOf(axialCoord, neighborCoord, neighborCoord1).sorted()
            val vertexTriple = Triple(vertexCoords[0], vertexCoords[1], vertexCoords[2])

            assertNotNull("Vertex should be created for direction $dir", board.getVertex(vertexTriple.first, vertexTriple.second, vertexTriple.third))
        }
    }

    // Getters

    @Test
    fun getEdge_ShouldReturnCorrectEdge() {
        val axialCoord1 = AxialCoord(0, 0)
        val axialCoord2 = AxialCoord(1, 0)
        val tile = Tile(axialCoord1, Resource.WOOD, 5)

        board.addTile(tile)

        val edge = board.getEdge(axialCoord1, axialCoord2)

        assertNotNull("Edge between $axialCoord1 and $axialCoord2 should exist", edge)
    }

    @Test
    fun getVertex_ShouldReturnCorrectVertex() {
        val axialCoord1 = AxialCoord(0, 0)
        val axialCoord2 = AxialCoord(1, 0)
        val axialCoord3 = AxialCoord(0, 1)
        val tile = Tile(axialCoord1, Resource.WHEAT, 9)

        board.addTile(tile)

        val vertex = board.getVertex(axialCoord1, axialCoord2, axialCoord3)

        assertNotNull("Vertex between $axialCoord1, $axialCoord2, and $axialCoord3 should exist", vertex)
    }

    // canPlaceRoad

    @Test
    fun testCanPlaceRoad_BuildingOnAdjacentVertex() {
        val tile = Tile(AxialCoord(0, 0), Resource.WOOD, 8)
        board.addTile(tile)

        val vertex = tile.vertices[Direction.NORTHEAST]!!

        vertex.placeBuilding(Building.SETTLEMENT,player) // Force SETTLEMENT building on this vertex
        val edge = tile.edges[Direction.NORTHEAST]!!

        assertTrue(board.canPlaceRoad(edge, player)) // Should return true if adjacent vertex has a building
    }

    @Test
    fun testCanPlaceRoad_RoadOnAdjacentEdge() {
        val tile = Tile(AxialCoord(0, 0), Resource.WOOD, 8)
        board.addTile(tile)

        val edge = tile.edges[Direction.NORTHEAST]!!
        edge.placeBuilding(Building.ROAD,player) // Force road on this edge

        val edge1 = tile.edges[Direction.EAST]!!
        assertTrue(board.canPlaceRoad(edge1, player)) // Should return true if adjacent edge has a road
    }

    @Test
    fun testCanPlaceRoad_BuildingOnEdge() {
        val tile = Tile(AxialCoord(0, 0), Resource.WOOD, 8)
        board.addTile(tile)

        val edge = tile.edges[Direction.NORTHEAST]!!
        edge.placeBuilding(Building.ROAD,player) // Force SETTLEMENT building on this edge

        assertFalse(board.canPlaceRoad(edge,player)) // Should return false if edge already has a building
    }

    @Test
    fun testCanPlaceRoad_NoBuildingOrRoad() {
        val tile = Tile(AxialCoord(0, 0), Resource.WOOD, 8)
        board.addTile(tile)
        val edge = tile.edges[Direction.EAST]!!

        assertFalse(board.canPlaceRoad(edge,player)) // Should return false if no building or road is adjacent
    }

    @Test
    fun testCanPLaceRoad_NoPlayerBuilding() {
        val tile = Tile(AxialCoord(0, 0), Resource.WOOD, 8)
        board.addTile(tile)

        val vertex = tile.vertices[Direction.NORTHEAST]!!
        vertex.placeBuilding(Building.SETTLEMENT,Player("Juan",Color.Red)) // Force road on this edge

        val edge1 = tile.edges[Direction.NORTHEAST]!!
        assertFalse(board.canPlaceRoad(edge1, player)) // Should return false, since Building is from another player
    }

    @Test
    fun testCanPLaceRoad_NoPlayerRoad() {
        val tile = Tile(AxialCoord(0, 0), Resource.WOOD, 8)
        board.addTile(tile)

        val edge = tile.edges[Direction.NORTHEAST]!!
        edge.placeBuilding(Building.ROAD,Player("Juan",Color.Red)) // Force road on this edge

        val edge1 = tile.edges[Direction.EAST]!!
        assertFalse(board.canPlaceRoad(edge1, player)) // Should return false, since Road is from another player
    }

    // canPlaceBuilding

    @Test
    fun testCanPlaceBuilding_NoBuildingOnVertex() {
        val tile = Tile(AxialCoord(0, 0), Resource.WOOD, 8)
        board.addTile(tile)

        val vertex = tile.vertices[Direction.NORTHEAST]!!
        assertTrue(board.canPlaceBuilding(vertex)) // Should return true if vertex has no building
    }

    @Test
    fun testCanPlaceBuilding_BuildingOnVertex() {
        val tile = Tile(AxialCoord(0, 0), Resource.WOOD, 8)
        board.addTile(tile)

        val vertex = tile.vertices[Direction.NORTHEAST]!!
        vertex.placeBuilding(Building.SETTLEMENT, player) // Force SETTLEMENT building on this vertex

        assertFalse(board.canPlaceBuilding(vertex, player)) // Should return false if vertex already has a building
    }

    @Test
    fun testCanPlaceBuilding_BuildingOnAdjacentVertex() {
        val tile = Tile(AxialCoord(0, 0), Resource.WOOD, 8)
        board.addTile(tile)

        val vertex = tile.vertices[Direction.NORTHEAST]!!
        val adjacentVertex = tile.vertices[Direction.EAST]!!
        adjacentVertex.placeBuilding(Building.SETTLEMENT, player) // Force SETTLEMENT building on adjacent vertex

        assertFalse(board.canPlaceBuilding(vertex, player)) // Should return false if adjacent vertex has a building
    }

    @Test
    fun testCanPlaceBuilding_RoadOnAdjacentEdge() {
        val tile = Tile(AxialCoord(0, 0), Resource.WOOD, 8)
        board.addTile(tile)

        val vertex = tile.vertices[Direction.NORTHEAST]!!
        val edge = tile.edges[Direction.NORTHEAST]!!
        edge.placeBuilding(Building.ROAD, player) // Force ROAD on adjacent edge

        assertTrue(board.canPlaceBuilding(vertex, player)) // Should return true if adjacent edge has a road owned by the player
    }

    @Test
    fun testCanPlaceBuilding_NoRoadOnAdjacentEdge() {
        val tile = Tile(AxialCoord(0, 0), Resource.WOOD, 8)
        board.addTile(tile)

        val vertex = tile.vertices[Direction.NORTHEAST]!!
        assertFalse(board.canPlaceBuilding(vertex, player)) // Should return false if no adjacent edge has a road
    }

    @Test
    fun testCanPlaceBuilding_RoadOnAdjacentEdgeNotOwnedByPlayer() {
        val tile = Tile(AxialCoord(0, 0), Resource.WOOD, 8)
        board.addTile(tile)

        val vertex = tile.vertices[Direction.NORTHEAST]!!
        val edge = tile.edges[Direction.NORTHEAST]!!
        edge.placeBuilding(Building.ROAD, Player("Juan",Color.Red)) // Force ROAD on adjacent edge owned by another player

        assertFalse(board.canPlaceBuilding(vertex, player)) // Should return false if adjacent edge has a road not owned by the player
    }

    // Resources
    @Test
    fun testGiveResourceShouldGiveSettlementResourceToPlayer(){
        val tile = Tile(AxialCoord(0, 0), Resource.WOOD, 8)
        board.addTile(tile)
        val vertex = tile.vertices[Direction.NORTHEAST]!!
        val vertex1 = tile.vertices[Direction.EAST]!!

        board.placeBuilding(vertex,Building.SETTLEMENT,player)
        board.placeBuilding(vertex1,Building.SETTLEMENT,Player("Juan",Color.Red))

        // Empty resource
        assertEquals(player.getDeckResources()[Resource.WOOD],null)

        // Give resource
        board.giveResource(8)
        assertEquals(player.getDeckResources()[Resource.WOOD],1)
    }

    @Test
    fun testGiveResourceShouldGiveCityResourceToPlayer(){
        val tile = Tile(AxialCoord(0, 0), Resource.WOOD, 8)
        board.addTile(tile)
        val vertex = tile.vertices[Direction.NORTHEAST]!!
        val vertex1 = tile.vertices[Direction.EAST]!!

        board.placeBuilding(vertex,Building.CITY,player)
        board.placeBuilding(vertex1,Building.SETTLEMENT,Player("Juan",Color.Red))

        // Empty resource
        assertEquals(player.getDeckResources()[Resource.WOOD],null)

        // Give resource
        board.giveResource(8)
        assertEquals(player.getDeckResources()[Resource.WOOD],2)
    }

    // Exceptions

    @Test(expected = InvalidBoardException::class)
    fun testGetVertexThrowsException() {
        // Coordinates outside the board's radius
        val coord1 = AxialCoord(4, 0) // Out of bounds
        val coord2 = AxialCoord(0, 4) // Out of bounds
        val coord3 = AxialCoord(4, -1) // Out of bounds

        board.getVertex(coord1, coord2, coord3)
    }

    @Test
    fun testGetVertexDoesNotThrowException() {
        // Coordinates outside the board's radius
        val coord1 = AxialCoord(4, 0) // Out of bounds
        val coord2 = AxialCoord(0, 2) // Out of bounds
        val coord3 = AxialCoord(4, -1) // Out of bounds

        board.getVertex(coord1, coord2, coord3)
    }

    @Test(expected = InvalidBoardException::class)
    fun testGetEdgeThrowsException() {
        // Coordinates outside the board's radius
        val coord1 = AxialCoord(-4, 0) // In bounds
        val coord2 = AxialCoord(0, 4) // In bounds

        board.getEdge(coord1, coord2)
    }

    @Test
    fun testGetEdgeDoesNotThrowException() {
        // Coordinates outside the board's radius
        val coord1 = AxialCoord(-4, 0) // Out of bounds
        val coord2 = AxialCoord(0, 3) // In bounds

        board.getEdge(coord1, coord2)
    }

    @Test(expected = InvalidBoardException::class)
    fun testGetTileThrowsException() {
        // Coordinate outside the board's radius
        val coord = AxialCoord(0, 4) // Out of bounds

        board.getTile(coord)
    }

}
