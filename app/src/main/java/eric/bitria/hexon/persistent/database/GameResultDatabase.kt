package eric.bitria.hexon.persistent.database

import android.content.Context
import androidx.room.*

@Database(entities = [GameResultEntity::class], version = 1)
@TypeConverters(GameResultConverters::class)
abstract class GameResultDatabase : RoomDatabase() {
    abstract fun gameResultDao(): GameResultDao

    companion object {
        @Volatile
        private var INSTANCE: GameResultDatabase? = null

        fun getDB(context: Context): GameResultDatabase {
            return INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    GameResultDatabase::class.java,
                    "game_result_db"
                ).build().also { INSTANCE = it }
            }
        }
    }
}
