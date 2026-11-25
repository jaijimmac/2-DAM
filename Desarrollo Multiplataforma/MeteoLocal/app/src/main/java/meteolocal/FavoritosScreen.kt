package meteolocal

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import data.local.LocalidadFavorita


@Composable
fun FavoritosScreen(
    favoritos: List<LocalidadFavorita>
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {
        Text(
            text = "Favoritos",
            style = MaterialTheme.typography.headlineMedium
        )
        Spacer(Modifier.height(16.dp))
        if (favoritos.isEmpty()) {
            Text("Todavía no tienes localidades favoritas.")
        } else {
            LazyColumn {
                items(favoritos) { localidad ->
                    FavoritoItem(localidad)
                }
            }
        }
    }
}

@Composable
fun FavoritoItem(localidad: LocalidadFavorita) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Text(
            text = "★ ${localidad.nombre}",
            style = MaterialTheme.typography.titleMedium
        )
        Text(
            text = "%.2f, %.2f".format(
                localidad.latitud,
                localidad.longitud
            )
        )
    }
}
