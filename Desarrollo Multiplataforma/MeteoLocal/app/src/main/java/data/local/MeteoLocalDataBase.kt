package data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
@Database(
    entities = [LocalidadFavorita::class],
    version = 1,
    exportSchema = false
)
abstract class MeteoLocalDatabase : RoomDatabase() {
    abstract fun localidadFavoritaDao(): LocalidadFavoritaDao
    companion object {
        @Volatile
        private var INSTANCE: MeteoLocalDatabase? = null
        fun getDatabase(context: Context): MeteoLocalDatabase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    MeteoLocalDatabase::class.java,
                    "meteolocal_database"
                ).build().also { INSTANCE = it }
            }
        }
    }
}
