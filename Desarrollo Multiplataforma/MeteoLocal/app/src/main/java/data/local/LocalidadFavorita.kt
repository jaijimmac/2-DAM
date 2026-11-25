package data.local
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "localidades_favoritas")
data class LocalidadFavorita(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val nombre: String,
    val latitud: Double,
    val longitud: Double,
    val alias: String = ""
)