package eric.bitria.hexon.persistent.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface GameResultDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(gameResult: GameResultEntity)

    @Query("SELECT * FROM game_results ORDER BY datePlayed DESC")
    fun getAll(): Flow<List<GameResultEntity>>

    @Query("DELETE FROM game_results")
    suspend fun clear()
}
