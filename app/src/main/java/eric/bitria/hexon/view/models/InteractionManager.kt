package eric.bitria.hexon.view.models

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import eric.bitria.hexon.src.board.tile.Edge
import eric.bitria.hexon.src.board.tile.Vertex
import eric.bitria.hexon.view.utils.CardType
import eric.bitria.hexon.view.utils.ClickHandler

class InteractionManager {
    var cardClickHandler by mutableStateOf<ClickHandler>(ClickHandler.None)
        private set
    var boardClickHandler by mutableStateOf<ClickHandler>(ClickHandler.None)
        internal set

    fun setNoParamCardClickHandler(handler: () -> Unit) {
        cardClickHandler = ClickHandler.NoParam(handler)
    }

    fun setOnCardClickHandler(handler: (CardType) -> Unit) {
        cardClickHandler = ClickHandler.OnCard(handler)
    }

    fun setOnVertexBoardClickHandler(handler: (Vertex) -> Unit) {
        boardClickHandler = ClickHandler.OnVertex(handler)
    }

    fun setOnEdgeBoardClickHandler(handler: (Edge) -> Unit) {
        boardClickHandler = ClickHandler.OnEdge(handler)
    }

    fun resetClickHandlers() {
        cardClickHandler = ClickHandler.None
        boardClickHandler = ClickHandler.None
    }
}
