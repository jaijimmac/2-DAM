package data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
@Dao
interface LocalidadFavoritaDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertar(localidad: LocalidadFavorita)
    @Query("SELECT * FROM localidades_favoritas ORDER BY nombre ASC")
    fun obtenerTodas(): Flow<List<LocalidadFavorita>>
}
