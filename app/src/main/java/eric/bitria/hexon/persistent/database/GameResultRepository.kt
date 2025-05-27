package eric.bitria.hexon.persistent.database

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GameResultRepository(private val dao: GameResultDao) {

    suspend fun saveGameResult(result: GameResult) {
        dao.insert(result.toEntity())
    }

    fun getAllResults(): Flow<List<GameResult>> {
        return dao.getAll().map { entities ->
            entities.map { it.toGameResult() }
        }
    }

    suspend fun clearResults() {
        dao.clear()
    }
}
