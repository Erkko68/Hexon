package eric.bitria.hexon.view.utils

import eric.bitria.hexon.src.data.game.Building
import eric.bitria.hexon.src.data.game.Resource
import eric.bitria.hexon.view.enums.GameActions

sealed class CardType {
    data class BuildingCard(val building: Building) : CardType()
    data class ResourceCard(val resource: Resource, val deck: DeckType) : CardType()
    data class ActionCard(val action: GameActions) : CardType()
}

sealed class DeckType {
    abstract val resource: Resource

    data class PlayerDeck(override val resource: Resource) : DeckType()
    data class SystemDeck(override val resource: Resource) : DeckType()
}