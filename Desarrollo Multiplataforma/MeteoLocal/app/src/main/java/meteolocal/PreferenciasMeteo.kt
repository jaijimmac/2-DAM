package meteolocal

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


val Context.dataStore by preferencesDataStore(
    name = "preferencias_meteolocal"
)

private val UNIDAD_TEMPERATURA =
    stringPreferencesKey("unidad_temperatura")

class PreferenciasMeteo(
    private val context: Context
) {
    val unidadTemperatura: Flow<String> =
        context.dataStore.data.map { preferencias ->
            preferencias[UNIDAD_TEMPERATURA] ?: "C"
        }

    suspend fun guardarUnidadTemperatura(
        unidad: String
    ) {
        context.dataStore.edit { preferencias ->
            preferencias[UNIDAD_TEMPERATURA] = unidad
        }
    }
}