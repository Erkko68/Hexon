package eric.bitria.hexon.src.board.tile

import eric.bitria.hexon.src.data.AxialCoord
import eric.bitria.hexon.src.data.game.Resource
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import org.junit.Before
import org.junit.Test

class TileTest {

    private lateinit var tile: Tile

    @Before
    fun setUp() {
        tile = Tile(AxialCoord(0, 0), Resource.WOOD, 8)
    }

    @Test
    fun tileShouldHaveCorrectResource(){
        assertEquals(Resource.WOOD, tile.resource)
    }

    @Test
    fun tileShouldHaveCorrectCoords(){
        val axialCoord = AxialCoord(0, 0);
        assertTrue(tile.coords == axialCoord)
    }

    @Test
    fun tileShouldHaveCorrectNumber(){
        assertEquals(8, tile.number)
    }
}