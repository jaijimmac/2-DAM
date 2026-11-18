package com.example.meteolocal;

import androidx.compose.runtime.Composable;
import androidx.compose.foundation.layout.Arrangement;
import androidx.compose.foundation.layout.Box;
import androidx.compose.foundation.layout.Column;
import androidx.compose.foundation.layout.Row;
import androidx.compose.foundation.layout.Spacer;
import androidx.compose.foundation.layout.fillMaxSize;
import androidx.compose.foundation.layout.fillMaxWidth;
import androidx.compose.foundation.layout.height;
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.meteolocal.network.ModelosMeteo.ResultadoMeteo

@Composable
fun ConsultaScreen(
    localidad: String,
    mensaje: String,
    mensajeUbicacion: String,
    onLocalidadChange: (String) -> Unit,
    onConsultar: () -> Unit,
    onSolicitarLimpiar: () -> Unit,

    onUsarUbicacion: () -> Unit,
    cargando: Boolean,
    error: String?,
    resultado: ResultadoMeteo?

) {

    var menuAbierto by remember {
        mutableStateOf(false)
    }

    var mostrarAcercaDe by remember {
        mutableStateOf(false)
    }

    val datos = resultado

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top

    ) {
        Text(
            text = "MeteoLocal",
            style = MaterialTheme.typography.headlineMedium
        )
        Box() {
            TextButton(
                onClick = {
                    menuAbierto = true
                }
            ) {
                Text("Más")
            }

            DropdownMenu(
                expanded = menuAbierto,
                onDismissRequest = {
                    menuAbierto = false
                }
            ) {
                DropdownMenuItem(
                    text = {
                        Text("Acerca de MeteoLocal")
                    },
                    onClick = {
                        menuAbierto = false
                        mostrarAcercaDe = true
                    }
                )
            }
        }
        Spacer(
            modifier = Modifier.height(16.dp)
        )
        Spacer(modifier = Modifier.height(24.dp))


        OutlinedTextField(
            value = localidad,
            onValueChange = { nuevoValor: String ->
                onLocalidadChange(nuevoValor)
            },
            label = { Text("Localidad") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(
            modifier = Modifier.height(16.dp)
        )
        Row {
            Button(
                onClick = onConsultar,
                enabled = !cargando
            ) {
                Text("Consultar")
            }
            Spacer(modifier = Modifier.width(12.dp))
            Button(onClick = onSolicitarLimpiar) {
                Text("Limpiar")
            }
        }
        Spacer(modifier = Modifier.height(12.dp))
        Button(onClick = onUsarUbicacion) {
            Text("Usar mi ubicación")
        }

        Spacer(modifier = Modifier.height(12.dp))
        Text(text = mensajeUbicacion)

        Spacer(modifier = Modifier.height(20.dp))

        when {
            cargando -> {
                CircularProgressIndicator()
                Spacer(Modifier.height(8.dp))
                Text("Consultando...")
            }
            error != null -> {
                Text(text = error.orEmpty())
            }
            datos != null -> {
                Text(
                    text = datos.localidad +
                            (datos.pais?.let { ", $it" } ?: ""),
                    style = MaterialTheme.typography.titleLarge
                )
                Text("Temperatura: ${datos.temperatura} °C")
                Text("Sensación: ${datos.sensacion} °C")
                Text("Humedad: ${datos.humedad} %")
                Text("Viento: ${datos.viento} km/h")
                Text("Código meteorológico: ${datos.codigo}")
            }
            else -> {
                Text("Introduce una localidad y pulsa Consultar.")
            }
        }
    }
    if (mostrarAcercaDe) {
        AlertDialog(
            onDismissRequest = {
                mostrarAcercaDe = false
            },
            title = {
                Text("MeteoLocal")
            },
            text = {
                Text(
                    "Aplicación desarrollada durante " +
                            "la Unidad 2 de PMDM."
                )
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        mostrarAcercaDe = false
                    }
                ) {
                    Text("Cerrar")
                }
            }
        )
    }
}