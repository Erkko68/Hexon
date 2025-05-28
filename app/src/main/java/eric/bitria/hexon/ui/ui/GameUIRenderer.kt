package eric.bitria.hexon.ui.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import eric.bitria.hexon.persistent.datastore.GameSettings
import eric.bitria.hexon.src.data.game.Building
import eric.bitria.hexon.src.data.game.Resource
import eric.bitria.hexon.src.player.Player
import eric.bitria.hexon.ui.ui.cards.ActionCard
import eric.bitria.hexon.ui.ui.cards.BuildingCard
import eric.bitria.hexon.ui.ui.cards.ResourceCard
import eric.bitria.hexon.view.enums.GameActions
import eric.bitria.hexon.view.enums.GamePhase
import eric.bitria.hexon.view.utils.CardType
import eric.bitria.hexon.view.utils.ClickHandler
import eric.bitria.hexon.view.utils.DeckType.PlayerDeck
import eric.bitria.hexon.view.utils.DeckType.SystemDeck
import kotlinx.coroutines.flow.Flow


val LocalCardSize = staticCompositionLocalOf<Dp> { error("Card size not provided") }
@Composable
fun GameUIRenderer(
    player: Player,
    currentPlayer: Player,
    players: List<Player>,
    phase: GamePhase,
    dices: Pair<Int, Int>,
    clickHandler: ClickHandler,
    timeLeft: Long,
    settingsFlow: Flow<GameSettings>
) {
    val settings = settingsFlow.collectAsState(initial = GameSettings()).value
    val configuration = LocalConfiguration.current
    val localCardSize = minOf(configuration.screenWidthDp.dp, configuration.screenHeightDp.dp) / 8f

    CompositionLocalProvider(LocalCardSize provides localCardSize) {
        Column(
            modifier = Modifier.fillMaxSize(),
        ) {
            // Top info section: players + info
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                PlayersColumn(
                    players = players,
                    currentPlayer = currentPlayer
                )

                InfoSection(
                    timeLeft = timeLeft,
                    player = player,
                    settings = settings,
                    modifier = Modifier.weight(1f)
                )
            }

            // Spacer fills remaining space pushing actions to bottom
            Spacer(modifier = Modifier.weight(1f))

            // Bottom actions section
            if (phase == GamePhase.ROLL_DICE) {
                DiceSection(
                    dices = dices,
                    enabled = (currentPlayer == player),
                    onRollClick = { (clickHandler as? ClickHandler.NoParam)?.handler?.invoke() },
                    modifier = Modifier.fillMaxWidth()
                )
            } else {
                Column(
                    modifier = Modifier
                        .padding(horizontal = 0.dp, vertical = 24.dp)
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        CardsContent(
                            player = player,
                            phase = phase,
                            clickHandler = clickHandler,
                            modifier = Modifier.weight(1f)
                        )
                        ActionContent(
                            phase = phase,
                            clickHandler = clickHandler,
                            player = player,
                            modifier = Modifier
                        )
                    }
                }
            }
        }
    }
}


@Composable
private fun InfoSection(
    modifier: Modifier = Modifier,
    timeLeft: Long = 0,
    player: Player,
    settings: GameSettings
) {
    val minutes = timeLeft / 60
    val seconds = timeLeft % 60

    val formattedTime = String.format("%d:%02d", minutes, seconds)

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.End
    ) {
        Box(
            modifier = Modifier
                .border(
                    width = 1.dp,
                    color = Color.Gray,
                    shape = RoundedCornerShape(8.dp)
                )
                .background(
                    color = Color.White,
                    shape = RoundedCornerShape(8.dp)
                )
                .padding(horizontal = 12.dp, vertical = 8.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = formattedTime,
                    color = Color.Black
                )
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    text = "🏆 ${player.getVictoryPoints()} / ${settings.victoryPoints}",
                    color = Color.Black
                )
            }
        }
    }
}

@Composable
private fun PlayersColumn(
    players: List<Player>,
    currentPlayer: Player,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalAlignment = Alignment.Start
    ) {
        players.forEach { p ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(horizontal = 8.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(if (p == currentPlayer) 40.dp else 36.dp)
                        .clip(CircleShape)
                        .background(p.color),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.AccountCircle,
                        contentDescription = "Player Icon",
                        tint = Color.White,
                        modifier = Modifier.fillMaxSize()
                    )
                }
                Text(
                    text = p.name,
                    modifier = Modifier.padding(start = 12.dp),
                    color = if (p == currentPlayer) Color.Black else Color.DarkGray,
                    fontWeight = if (p == currentPlayer) FontWeight.Bold else FontWeight.Normal
                )
            }
        }
    }
}


@Composable
private fun DiceSection(
    dices: Pair<Int, Int>,
    onRollClick: () -> Unit,
    enabled: Boolean,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier) {
        DiceScreen(
            dice1 = dices.first,
            dice2 = dices.second,
            enabled = enabled,
            onRollClick = onRollClick
        )
    }
}

@Composable
private fun ActionContent(
    phase: GamePhase,
    clickHandler: ClickHandler,
    modifier: Modifier,
    player: Player
) {
    val availableActions = when (phase) {
        GamePhase.PLAYER_TRADE -> listOf(
            GameActions.END_TURN,
            GameActions.CLOSE_TRADE,
            GameActions.ACCEPT_TRADE
        ).sorted().toTypedArray()
        else -> listOf(
            GameActions.END_TURN,
            GameActions.OPEN_TRADE
        ).sorted().toTypedArray()
    }

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.End
    ) {
        ActionsContainer(cards = availableActions) { action ->
            val isActionAllowedPhase = phase in listOf(GamePhase.PLAYER_TURN, GamePhase.PLAYER_TRADE)
            val canPerformAction = if (action == GameActions.ACCEPT_TRADE) {
                player.canAcceptTrade()
            } else true

            ActionCard(
                action = action,
                onClick = {
                    (clickHandler as? ClickHandler.OnCard)
                        ?.handler(CardType.ActionCard(action))
                },
                enabled = isActionAllowedPhase && canPerformAction
            )
        }
    }
}


@Composable
private fun CardsContent(
    player: Player,
    phase: GamePhase,
    clickHandler: ClickHandler,
    modifier: Modifier
) {
    val resources = player.getDeckResources().filter { it.value > 0 }.keys.sorted().toTypedArray()
    val allResources = Resource.entries.filter { it != Resource.NONE }.sorted().toTypedArray()
    val buildings = Building.entries.filter { it != Building.NONE }.sorted().toTypedArray()

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        if (phase == GamePhase.PLAYER_TRADE){
            CardsContainer(
                cards = allResources
            ) { resource ->
                ResourceCard(
                    resource = resource,
                    count = player.getSystemTradeDeckResources()[resource] ?: 0,
                    onClick = { (clickHandler as? ClickHandler.OnCard)?.handler(
                        CardType.ResourceCard(
                            resource,
                            SystemDeck(resource)
                        )
                    ) }
                )
            }
        } else {
            CardsContainer(
                cards = buildings
            ) { building ->
                BuildingCard(
                    building = building,
                    onClick = { (clickHandler as? ClickHandler.OnCard)?.handler(
                        CardType.BuildingCard(
                            building
                        )
                    ) },
                    enabled = player.hasBuildingResources(building) && (phase == GamePhase.PLAYER_TURN)
                )
            }
        }
        Spacer(modifier = Modifier.padding(8.dp))

        CardsContainer(
            cards = resources
        ) { resource ->
            ResourceCard(
                enabled = true,
                selected = player.getPlayerTradeDeckResources()[resource] ?: 0,
                resource = resource,
                count = player.getDeckResources()[resource] ?: 0,
                onClick = { (clickHandler as? ClickHandler.OnCard)?.handler(
                    CardType.ResourceCard(
                        resource,
                        PlayerDeck(resource)
                    )
                ) }
            )
        }
    }
}