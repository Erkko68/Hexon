package eric.bitria.hexon.view

import android.app.Application
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import eric.bitria.hexon.dataStore
import eric.bitria.hexon.persistent.database.GameResult
import eric.bitria.hexon.persistent.database.GameResultDatabase
import eric.bitria.hexon.persistent.database.GameResultRepository
import eric.bitria.hexon.persistent.database.PlayerStats
import eric.bitria.hexon.persistent.datastore.GameSettings
import eric.bitria.hexon.src.board.Board
import eric.bitria.hexon.src.board.tile.Edge
import eric.bitria.hexon.src.board.tile.Vertex
import eric.bitria.hexon.src.data.game.Building
import eric.bitria.hexon.src.data.game.Resource
import eric.bitria.hexon.src.player.Player
import eric.bitria.hexon.view.enums.GameActions
import eric.bitria.hexon.view.enums.GamePhase
import eric.bitria.hexon.view.models.BoardManager
import eric.bitria.hexon.view.models.GameSettingsManager
import eric.bitria.hexon.view.models.GameStatusManager
import eric.bitria.hexon.view.models.InteractionManager
import eric.bitria.hexon.view.models.LogEntry
import eric.bitria.hexon.view.models.LogManager
import eric.bitria.hexon.view.models.PlayerManager
import eric.bitria.hexon.view.models.TurnTimerManager
import eric.bitria.hexon.view.utils.Bot
import eric.bitria.hexon.view.utils.CardType
import eric.bitria.hexon.view.utils.ClickHandler
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class MainGameViewModel(application: Application) : AndroidViewModel(application) {
    private val dataStore = application.dataStore
    val settingsManager = GameSettingsManager(dataStore, viewModelScope)

    private val repository = GameResultRepository(GameResultDatabase.getDB(getApplication()).gameResultDao())

    val boardManager = BoardManager()
    val playerManager = PlayerManager()
    val gameStatusManager = GameStatusManager()
    val timerViewModel = TurnTimerManager(viewModelScope)
    val interactionManager = InteractionManager()
    private val logManager = LogManager()

    private lateinit var turnManager: TurnManager

    // Expose states for the UI from sub-ViewModels
    val gameLogs: List<LogEntry> get() = logManager.logs
    val gameSettingsFlow: Flow<GameSettings> get() = settingsManager.settings
    val history: Flow<List<GameResult>> get() = repository.getAllResults()
    val board: Board get() = boardManager.board
    val humanPlayer: Player get() = playerManager.humanPlayer
    val currentPlayer: Player get() = playerManager.currentPlayer
    val gamePhase: GamePhase get() = gameStatusManager.gamePhase
    val dices: Pair<Int, Int> get() = gameStatusManager.dices
    val timeLeft: Long get() = timerViewModel.timeLeftSeconds
    val cardClickHandler: ClickHandler get() = interactionManager.cardClickHandler
    val boardClickHandler: ClickHandler get() = interactionManager.boardClickHandler
    val availableVertices: List<Vertex> get() = boardManager.availableVertices
    val availableEdges: List<Edge> get() = boardManager.availableEdges

    fun updatePlayerName(name: String) = settingsManager.updatePlayerName(name)
    fun updatePlayerColor(color: Color) = settingsManager.updatePlayerColor(color)
    fun updatePlayerEmail(email: String) = settingsManager.updatePlayerEmail(email)
    fun updateNumberOfBots(bots: Int) = settingsManager.updateNumberOfBots(bots)
    fun updateVictoryPoints(points: Int) = settingsManager.updateVictoryPoints(points)
    fun updateTimer(timer: Long) = settingsManager.updateTimer(timer)

    fun startNewGame() {
        logManager.clearLogs()
        playerManager.setupPlayers(settingsManager.settings.value)
        boardManager.initializeBoard()
        timerViewModel.setTurnDuration(settingsManager.settings.value.timer)

        turnManager = TurnManager(playerManager.allPlayers, onRotationComplete = ::handleRotationComplete)
        playerManager.updateCurrentPlayer(turnManager.getCurrentPlayer())

        interactionManager.resetClickHandlers()
        boardManager.clearHighlights()

        proceedToInitialSettlementPlacement()
    }

    private fun proceedToInitialSettlementPlacement() {
        gameStatusManager.setPhase(GamePhase.INITIAL_PLACEMENT)
        val currentP = playerManager.currentPlayer

        if (currentP.isBot) {
            viewModelScope.launch {
                delay(1000) // Bot thinking time
                Bot.initialSettlementPlacement(boardManager.board, currentP, logManager)
                proceedToInitialRoadPlacement()
            }
        } else {
            boardManager.highlightInitialSettlementVertices()
            interactionManager.setOnVertexBoardClickHandler { vertex ->
                interactionManager.resetClickHandlers()
                boardManager.clearHighlights()
                GameManager.placeInitialSettlement(boardManager.board, currentP, vertex, logManager)
                proceedToInitialRoadPlacement()
            }
        }
    }

    private fun proceedToInitialRoadPlacement() {
        gameStatusManager.setPhase(GamePhase.INITIAL_PLACEMENT) // Still initial placement
        val currentP = playerManager.currentPlayer

        if (currentP.isBot) {
            viewModelScope.launch {
                delay(1000) // Bot thinking time
                Bot.placeRoad(boardManager.board, currentP, logManager) // Assuming Bot.placeRoad exists for initial
                endInitialPlacementTurn()
            }
        } else {
            boardManager.highlightRoadEdgesForInitialPlacement(currentP) // Use specific or general highlighting
            interactionManager.setOnEdgeBoardClickHandler { edge ->
                interactionManager.resetClickHandlers()
                boardManager.clearHighlights()
                GameManager.placeInitialRoad(boardManager.board, currentP, edge, logManager)
                endInitialPlacementTurn()
            }
        }
    }

    private fun endInitialPlacementTurn() {
        playerManager.updateCurrentPlayer(turnManager.nextTurn())
        // onRotationComplete callback in TurnManager will handle phase transition or next player's placement
        if (gameStatusManager.gamePhase != GamePhase.ROLL_DICE) { // If not transitioned by onRotationComplete
            proceedToInitialSettlementPlacement()
        } else { // Transitioned to normal game
            proceedToDiceRollPhase()
        }
    }

    private fun handleRotationComplete() {
        if (gameStatusManager.gamePhase == GamePhase.INITIAL_PLACEMENT) {
            if (!turnManager.hasReversedTurnOrder()) {
                turnManager.reverseTurnOrder()
                // The turn manager might automatically set the next player, or we might need to update it.
                playerManager.updateCurrentPlayer(turnManager.getCurrentPlayer())
                // Continue placement with reversed order
            } else {
                // Both forward and reverse initial placements are done
                gameStatusManager.setPhase(GamePhase.ROLL_DICE)
                playerManager.updateCurrentPlayer(turnManager.getCurrentPlayer()) // First player of normal round
                // proceedToDiceRollPhase() will be called by endInitialPlacementTurn's logic
            }
        }
        // If in a different phase, onRotationComplete might mean something else or nothing.
    }

    private fun proceedToDiceRollPhase() {
        gameStatusManager.setPhase(GamePhase.ROLL_DICE)
        playerManager.updateCurrentPlayer(turnManager.getCurrentPlayer())
        interactionManager.resetClickHandlers()
        boardManager.clearHighlights()
        timerViewModel.stopTimer() // Stop previous timer if any

        val currentP = playerManager.currentPlayer

        if (currentP.isBot) {
            viewModelScope.launch {
                delay(1000)
                val (d1, d2) = GameManager.rollDice(boardManager.board)
                gameStatusManager.updateDiceRoll(d1, d2)
                logManager.addLog("[${currentP.name}] rolled dice: ${d1+d2}", currentP.color)
                delay(1000)
                proceedToPlayerTurnPhase()
            }
        } else {
            timerViewModel.startTimer { // Timer for dice roll action
                // Auto-roll if player doesn't click in time
                val (d1, d2) = GameManager.rollDice(boardManager.board)
                gameStatusManager.updateDiceRoll(d1, d2)
                interactionManager.resetClickHandlers() // Important
                viewModelScope.launch { delay(500); proceedToPlayerTurnPhase() }
            }
            interactionManager.setNoParamCardClickHandler { // For clicking the dice UI
                timerViewModel.stopTimer()
                val (d1, d2) = GameManager.rollDice(boardManager.board)
                gameStatusManager.updateDiceRoll(d1, d2)
                logManager.addLog("[${currentP.name}] rolled dice: ${d1+d2}", currentP.color)
                interactionManager.resetClickHandlers() // Only one roll
                viewModelScope.launch { delay(500); proceedToPlayerTurnPhase() }
            }
        }
    }

    private fun proceedToPlayerTurnPhase() {
        val currentP = playerManager.currentPlayer
        gameStatusManager.setPhase(if (currentP.isBot) GamePhase.BOT_TURN else GamePhase.PLAYER_TURN)

        if (currentP.isBot) {
            viewModelScope.launch {
                delay(1000)
                Bot.placeRoad(boardManager.board, currentP, logManager)
                delay(500)
                Bot.placeSettlement(boardManager.board, currentP, logManager)
                delay(1000)
                processTurnEnd()
            }
        } else {
            timerViewModel.startTimer { processTurnEnd() } // Start main turn timer
            // Setup card click handlers for player actions
            interactionManager.setOnCardClickHandler { cardType ->
                handleCardTypeClick(cardType)
            }
        }
    }

    private fun handleCardTypeClick(card: CardType) {
        val currentP = playerManager.currentPlayer
        if (currentP.isBot) return // Should not happen if UI is disabled for bots

        boardManager.clearHighlights()

        when (card) {
            is CardType.BuildingCard -> {
                when (card.building) {
                    Building.SETTLEMENT -> {
                        boardManager.highlightSettlementVertices(currentP)
                        interactionManager.setOnVertexBoardClickHandler { vertex ->
                            GameManager.placeSettlement(boardManager.board, currentP, vertex, logManager)
                            interactionManager.resetClickHandlers() // Or return to main turn action handlers
                            boardManager.clearHighlights()
                            setupPlayerTurnInteractions() // Re-setup general turn interactions
                        }
                    }
                    Building.ROAD -> {
                        boardManager.highlightRoadEdges(currentP)
                        interactionManager.setOnEdgeBoardClickHandler { edge ->
                            GameManager.placeRoad(boardManager.board, currentP, edge, logManager)
                            interactionManager.resetClickHandlers()
                            boardManager.clearHighlights()
                            setupPlayerTurnInteractions()
                        }
                    }
                    Building.CITY -> {
                        boardManager.highlightCityUpgradableVertices(currentP)
                        interactionManager.setOnVertexBoardClickHandler { vertex ->
                            GameManager.placeCity(boardManager.board, currentP, vertex, logManager)
                            interactionManager.resetClickHandlers()
                            boardManager.clearHighlights()
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
                if (gameStatusManager.gamePhase == GamePhase.PLAYER_TRADE) {
                    playerManager.handleResourceClickForTrade(card.resource, card.deck)
                    // UI should update based on playerViewModel.currentPlayer's trading state
                }
            }
        }
    }

    private fun setupPlayerTurnInteractions() {
        // Re-establishes the general click handler for cards during a player's turn
        interactionManager.setOnCardClickHandler { cardType ->
            handleCardTypeClick(cardType)
        }
        // If no specific sub-action is active, board clicks do nothing.
        if (interactionManager.boardClickHandler !is ClickHandler.OnVertex && interactionManager.boardClickHandler !is ClickHandler.OnEdge) {
            interactionManager.boardClickHandler = ClickHandler.None
        }
    }


    private fun handleGameAction(action: GameActions) {
        boardManager.clearHighlights()
        interactionManager.resetClickHandlers() // Reset board click first

        when (action) {
            GameActions.OPEN_TRADE -> {
                gameStatusManager.setPhase(GamePhase.PLAYER_TRADE)
                // UI should now show trading interface. Resource card clicks are handled by handleCardTypeClick
                setupPlayerTurnInteractions()
            }
            GameActions.CLOSE_TRADE -> {
                playerManager.cancelCurrentPlayerTrade()
                gameStatusManager.setPhase(GamePhase.PLAYER_TURN)
                setupPlayerTurnInteractions()
            }
            GameActions.ACCEPT_TRADE -> {
                playerManager.acceptCurrentPlayerTrade()
                gameStatusManager.setPhase(GamePhase.PLAYER_TURN)
                setupPlayerTurnInteractions()
            }
            GameActions.END_TURN -> {
                processTurnEnd()
            }
        }
    }

    private fun processTurnEnd() {
        timerViewModel.stopTimer()
        playerManager.cancelCurrentPlayerTrade() // Ensure trade is cancelled
        interactionManager.resetClickHandlers()
        boardManager.clearHighlights()

        if (playerManager.getCurrentPlayerVictoryPoints() >= settingsManager.settings.value.victoryPoints) {
            gameStatusManager.setPhase(GamePhase.PLAYER_WON)
            saveCurrentGameResult()
            return
        }

        gameStatusManager.setPhase(GamePhase.END_TURN) // Brief phase
        playerManager.updateCurrentPlayer(turnManager.nextTurn())
        proceedToDiceRollPhase()
    }

    fun generateGameResult(): GameResult {
        val dateFormat = SimpleDateFormat("MMMM d, yyyy 'at' h:mm a", Locale.getDefault())
        val currentTime = dateFormat.format(Date())

        val allStats = playerManager.allPlayers.map { player ->

            val buildings = player.getTotalBuildings()
                .filterKeys { it != Building.NONE }
                .mapKeys { it.key.name }

            val resources = player.getTotalResources()
                .filterKeys { it != Resource.NONE }
                .mapKeys { it.key.name }

            PlayerStats(
                playerName = player.name,
                playerColor = player.color,
                buildingStats = buildings,
                resourceStats = resources
            )
        }

        return GameResult(
            datePlayed = currentTime,
            winnerName = currentPlayer.name,
            playerStats = allStats
        )
    }

    fun saveCurrentGameResult() {
        val result = generateGameResult()
        viewModelScope.launch {
            repository.saveGameResult(result)
        }
    }

}