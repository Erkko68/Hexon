package eric.bitria.hexon.src.board.tile

import eric.bitria.hexon.src.data.Coord
import eric.bitria.hexon.src.data.Resource
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import org.junit.Before
import org.junit.Test

class TileTest {

    private lateinit var tile: Tile

    @Before
    fun setUp() {
        tile = Tile(Coord(0, 0), Resource.WOOD, 8)
    }

    @Test
    fun tileShouldHaveCorrectResource(){
        assertEquals(Resource.WOOD, tile.resource)
    }

    @Test
    fun tileShouldHaveCorrectCoords(){
        val coord = Coord(0, 0);
        assertTrue(tile.coords == coord)
    }

    @Test
    fun tileShouldHaveCorrectNumber(){
        assertEquals(8, tile.number)
    }
}