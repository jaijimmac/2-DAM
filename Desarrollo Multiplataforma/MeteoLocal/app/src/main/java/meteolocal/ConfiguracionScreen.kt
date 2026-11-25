package meteolocal

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ConfiguracionScreen(
    unidadTemperatura: String,
    onUnidadChange: (String) -> Unit
) {
    Column(
        modifier = Modifier.padding(24.dp)
    ) {
        Text(
            text = "Configuración",
            style = MaterialTheme.typography.headlineMedium
        )
        Spacer(Modifier.height(24.dp))
        Text("Unidad de temperatura")
        Row(verticalAlignment = Alignment.CenterVertically) {
            RadioButton(
                selected = unidadTemperatura == "C",
                onClick = { onUnidadChange("C") }
            )
            Text("Celsius (°C)")
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
            RadioButton(
                selected = unidadTemperatura     == "F",
                onClick = { onUnidadChange("F") }
            )
            Text("Fahrenheit (°F)")
        }
    }
    }