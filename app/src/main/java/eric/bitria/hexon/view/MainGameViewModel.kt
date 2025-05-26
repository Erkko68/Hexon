package eric.bitria.hexon.view

import eric.bitria.hexon.view.models.GameSettingsViewModel
import android.app.Application
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import eric.bitria.hexon.dataStore
import eric.bitria.hexon.src.board.Board
import eric.bitria.hexon.src.board.tile.Edge
import eric.bitria.hexon.src.board.tile.Vertex
import eric.bitria.hexon.src.data.game.Building
import eric.bitria.hexon.src.player.Player
import eric.bitria.hexon.ui.screen.GameSettings
import eric.bitria.hexon.view.enums.GameActions
import eric.bitria.hexon.view.enums.GamePhase
import eric.bitria.hexon.view.models.BoardViewModel
import eric.bitria.hexon.view.models.GameStatusViewModel
import eric.bitria.hexon.view.models.InteractionViewModel
import eric.bitria.hexon.view.models.PlayerViewModel
import eric.bitria.hexon.view.models.TurnTimerViewModel
import eric.bitria.hexon.view.utils.Bot
import eric.bitria.hexon.view.utils.CardType
import eric.bitria.hexon.view.utils.ClickHandler
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainGameViewModel(application: Application) : AndroidViewModel(application) {
    private val dataStore = application.dataStore
    val settingsViewModel = GameSettingsViewModel(dataStore, viewModelScope)

    val boardViewModel = BoardViewModel()
    val playerViewModel = PlayerViewModel()
    val gameStatusViewModel = GameStatusViewModel()
    val timerViewModel = TurnTimerViewModel(viewModelScope)
    val interactionViewModel = InteractionViewModel()

    private lateinit var turnManager: TurnManager

    // Expose states for the UI from sub-ViewModels
    val gameSettings: GameSettings get() = settingsViewModel.settings
    val board: Board get() = boardViewModel.board
    val humanPlayer: Player get() = playerViewModel.humanPlayer
    val currentPlayer: Player get() = playerViewModel.currentPlayer
    val gamePhase: GamePhase get() = gameStatusViewModel.gamePhase
    val dices: Pair<Int, Int> get() = gameStatusViewModel.dices
    val timeLeft: Long get() = timerViewModel.timeLeftSeconds
    val cardClickHandler: ClickHandler get() = interactionViewModel.cardClickHandler
    val boardClickHandler: ClickHandler get() = interactionViewModel.boardClickHandler
    val availableVertices: List<Vertex> get() = boardViewModel.availableVertices
    val availableEdges: List<Edge> get() = boardViewModel.availableEdges


    fun updatePlayerName(name: String) = settingsViewModel.updatePlayerName(name)
    fun updatePlayerColor(color: Color) = settingsViewModel.updatePlayerColor(color)
    fun updateNumberOfBots(bots: Int) = settingsViewModel.updateNumberOfBots(bots)
    fun updateVictoryPoints(points: Int) = settingsViewModel.updateVictoryPoints(points)
    fun updateTimer(timer: Long) = settingsViewModel.updateTimer(timer)
    fun updateGameConfig(newConfig: GameSettings) = settingsViewModel.updateGameSettings(newConfig)


    fun startNewGame() {
        playerViewModel.setupPlayers(settingsViewModel.settings)
        boardViewModel.initializeBoard()
        timerViewModel.setTurnDuration(settingsViewModel.settings.timer)
        timerViewModel.resetTotalTime()

        turnManager = TurnManager(playerViewModel.allPlayers, onRotationComplete = ::handleRotationComplete)
        playerViewModel.updateCurrentPlayer(turnManager.getCurrentPlayer())

        interactionViewModel.resetClickHandlers()
        boardViewModel.clearHighlights()

        proceedToInitialSettlementPlacement()
    }

    private fun proceedToInitialSettlementPlacement() {
        gameStatusViewModel.setPhase(GamePhase.INITIAL_PLACEMENT)
        val currentP = playerViewModel.currentPlayer

        if (currentP.isBot) {
            viewModelScope.launch {
                delay(1000) // Bot thinking time
                Bot.initialSettlementPlacement(boardViewModel.board, currentP)
                boardViewModel.updateBoardSnapshot() // Reflect changes if any
                proceedToInitialRoadPlacement()
            }
        } else {
            boardViewModel.highlightInitialSettlementVertices()
            interactionViewModel.setOnVertexBoardClickHandler { vertex ->
                interactionViewModel.resetClickHandlers()
                boardViewModel.clearHighlights()
                GameManager.placeInitialSettlement(boardViewModel.board, currentP, vertex)
                boardViewModel.updateBoardSnapshot()
                proceedToInitialRoadPlacement()
            }
        }
    }

    private fun proceedToInitialRoadPlacement() {
        gameStatusViewModel.setPhase(GamePhase.INITIAL_PLACEMENT) // Still initial placement
        val currentP = playerViewModel.currentPlayer

        if (currentP.isBot) {
            viewModelScope.launch {
                delay(1000) // Bot thinking time
                Bot.placeRoad(boardViewModel.board, currentP) // Assuming Bot.placeRoad exists for initial
                boardViewModel.updateBoardSnapshot()
                endInitialPlacementTurn()
            }
        } else {
            boardViewModel.highlightRoadEdgesForInitialPlacement(currentP) // Use specific or general highlighting
            interactionViewModel.setOnEdgeBoardClickHandler { edge ->
                interactionViewModel.resetClickHandlers()
                boardViewModel.clearHighlights()
                GameManager.placeInitialRoad(boardViewModel.board, currentP, edge)
                boardViewModel.updateBoardSnapshot()
                endInitialPlacementTurn()
            }
        }
    }

    private fun endInitialPlacementTurn() {
        playerViewModel.updateCurrentPlayer(turnManager.nextTurn())
        // onRotationComplete callback in TurnManager will handle phase transition or next player's placement
        if (gameStatusViewModel.gamePhase != GamePhase.ROLL_DICE) { // If not transitioned by onRotationComplete
            proceedToInitialSettlementPlacement()
        } else { // Transitioned to normal game
            proceedToDiceRollPhase()
        }
    }

    private fun handleRotationComplete() {
        if (gameStatusViewModel.gamePhase == GamePhase.INITIAL_PLACEMENT) {
            if (!turnManager.hasReversedTurnOrder()) {
                turnManager.reverseTurnOrder()
                // The turn manager might automatically set the next player, or we might need to update it.
                playerViewModel.updateCurrentPlayer(turnManager.getCurrentPlayer())
                // Continue placement with reversed order
            } else {
                // Both forward and reverse initial placements are done
                gameStatusViewModel.setPhase(GamePhase.ROLL_DICE)
                playerViewModel.updateCurrentPlayer(turnManager.getCurrentPlayer()) // First player of normal round
                // proceedToDiceRollPhase() will be called by endInitialPlacementTurn's logic
            }
        }
        // If in a different phase, onRotationComplete might mean something else or nothing.
    }

    private fun proceedToDiceRollPhase() {
        gameStatusViewModel.setPhase(GamePhase.ROLL_DICE)
        playerViewModel.updateCurrentPlayer(turnManager.getCurrentPlayer())
        interactionViewModel.resetClickHandlers()
        boardViewModel.clearHighlights()
        timerViewModel.stopTimer() // Stop previous timer if any

        val currentP = playerViewModel.currentPlayer

        if (currentP.isBot) {
            viewModelScope.launch {
                delay(1000)
                val (d1, d2) = GameManager.rollDice(boardViewModel.board)
                gameStatusViewModel.updateDiceRoll(d1, d2)
                boardViewModel.updateBoardSnapshot() // Resource distribution might change board view
                delay(1000)
                proceedToPlayerTurnPhase()
            }
        } else {
            timerViewModel.startTimer { // Timer for dice roll action
                // Auto-roll if player doesn't click in time
                val (d1, d2) = GameManager.rollDice(boardViewModel.board)
                gameStatusViewModel.updateDiceRoll(d1, d2)
                boardViewModel.updateBoardSnapshot()
                interactionViewModel.resetClickHandlers() // Important
                viewModelScope.launch { delay(500); proceedToPlayerTurnPhase() }
            }
            interactionViewModel.setNoParamCardClickHandler { // For clicking the dice UI
                timerViewModel.stopTimer()
                val (d1, d2) = GameManager.rollDice(boardViewModel.board)
                gameStatusViewModel.updateDiceRoll(d1, d2)
                boardViewModel.updateBoardSnapshot()
                interactionViewModel.resetClickHandlers() // Only one roll
                viewModelScope.launch { delay(500); proceedToPlayerTurnPhase() }
            }
        }
    }

    private fun proceedToPlayerTurnPhase() {
        val currentP = playerViewModel.currentPlayer
        gameStatusViewModel.setPhase(if (currentP.isBot) GamePhase.BOT_TURN else GamePhase.PLAYER_TURN)

        if (currentP.isBot) {
            viewModelScope.launch {
                delay(1000)
                Bot.placeRoad(boardViewModel.board, currentP)
                boardViewModel.updateBoardSnapshot()
                delay(500)
                Bot.placeSettlement(boardViewModel.board, currentP)
                boardViewModel.updateBoardSnapshot()
                delay(1000)
                processTurnEnd()
            }
        } else {
            timerViewModel.startTimer { processTurnEnd() } // Start main turn timer
            // Setup card click handlers for player actions
            interactionViewModel.setOnCardClickHandler { cardType ->
                handleCardTypeClick(cardType)
            }
        }
    }

    private fun handleCardTypeClick(card: CardType) {
        val currentP = playerViewModel.currentPlayer
        if (currentP.isBot) return // Should not happen if UI is disabled for bots

        boardViewModel.clearHighlights()

        when (card) {
            is CardType.BuildingCard -> {
                when (card.building) {
                    Building.SETTLEMENT -> {
                        boardViewModel.highlightSettlementVertices(currentP)
                        interactionViewModel.setOnVertexBoardClickHandler { vertex ->
                            GameManager.placeSettlement(boardViewModel.board, currentP, vertex)
                            boardViewModel.updateBoardSnapshot()
                            interactionViewModel.resetClickHandlers() // Or return to main turn action handlers
                            boardViewModel.clearHighlights()
                            setupPlayerTurnInteractions() // Re-setup general turn interactions
                        }
                    }
                    Building.ROAD -> {
                        boardViewModel.highlightRoadEdges(currentP)
                        interactionViewModel.setOnEdgeBoardClickHandler { edge ->
                            GameManager.placeRoad(boardViewModel.board, currentP, edge)
                            boardViewModel.updateBoardSnapshot()
                            interactionViewModel.resetClickHandlers()
                            boardViewModel.clearHighlights()
                            setupPlayerTurnInteractions()
                        }
                    }
                    Building.CITY -> {
                        boardViewModel.highlightCityUpgradableVertices(currentP)
                        interactionViewModel.setOnVertexBoardClickHandler { vertex ->
                            GameManager.placeCity(boardViewModel.board, currentP, vertex)
                            boardViewModel.updateBoardSnapshot()
                            interactionViewModel.resetClickHandlers()
                            boardViewModel.clearHighlights()
                            setupPlayerTurnInteractions()
                        }
                    }
                    Building.NONE -> {}
                }
            }
            is CardType.ActionCard -> {
                handleGameAction(card.action)
            }
            is CardType.ResourceCard -> {
                // This is usually for trading context
                if (gameStatusViewModel.gamePhase == GamePhase.PLAYER_TRADE) {
                    playerViewModel.handleResourceClickForTrade(card.resource, card.deck)
                    // UI should update based on playerViewModel.currentPlayer's trading state
                }
            }
        }
    }

    private fun setupPlayerTurnInteractions() {
        // Re-establishes the general click handler for cards during a player's turn
        interactionViewModel.setOnCardClickHandler { cardType ->
            handleCardTypeClick(cardType)
        }
        // Ensure board click handler is None unless a specific action (like placing a building) sets it.
        // If a building card was clicked, that specific board handler is already set.
        // If not, ensure it's reset or set to a default "do nothing" or "select tile" handler if desired.
        // For now, we assume handleCardTypeClick sets specific board handlers, and otherwise it should be None.
        // If no specific sub-action is active, board clicks do nothing.
        if (interactionViewModel.boardClickHandler !is ClickHandler.OnVertex && interactionViewModel.boardClickHandler !is ClickHandler.OnEdge) {
            interactionViewModel.boardClickHandler = ClickHandler.None
        }
    }


    private fun handleGameAction(action: GameActions) {
        boardViewModel.clearHighlights()
        interactionViewModel.resetClickHandlers() // Reset board click first

        when (action) {
            GameActions.OPEN_TRADE -> {
                gameStatusViewModel.setPhase(GamePhase.PLAYER_TRADE)
                // UI should now show trading interface. Resource card clicks are handled by handleCardTypeClick
                // Still need to set up general turn interactions for other cards
                setupPlayerTurnInteractions()
            }
            GameActions.CLOSE_TRADE -> {
                playerViewModel.cancelCurrentPlayerTrade()
                gameStatusViewModel.setPhase(GamePhase.PLAYER_TURN)
                setupPlayerTurnInteractions()
            }
            GameActions.ACCEPT_TRADE -> {
                playerViewModel.acceptCurrentPlayerTrade()
                gameStatusViewModel.setPhase(GamePhase.PLAYER_TURN)
                // Potentially update board/player states if trade affected something visible beyond resources
                setupPlayerTurnInteractions()
            }
            GameActions.END_TURN -> {
                processTurnEnd()
            }
        }
    }

    private fun processTurnEnd() {
        timerViewModel.stopTimer()
        playerViewModel.cancelCurrentPlayerTrade() // Ensure trade is cancelled
        interactionViewModel.resetClickHandlers()
        boardViewModel.clearHighlights()

        if (playerViewModel.getCurrentPlayerVictoryPoints() >= settingsViewModel.settings.victoryPoints) {
            gameStatusViewModel.setPhase(GamePhase.PLAYER_WON)
            // Handle game over screen display
            return
        }

        gameStatusViewModel.setPhase(GamePhase.END_TURN) // Brief phase
        playerViewModel.updateCurrentPlayer(turnManager.nextTurn())
        proceedToDiceRollPhase()
    }
}