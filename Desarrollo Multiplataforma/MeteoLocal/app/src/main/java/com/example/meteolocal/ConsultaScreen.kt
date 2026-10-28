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
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
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

@Composable
fun ConsultaScreen(
    localidad: String,
    mensaje: String,
    onLocalidadChange: (String) -> Unit,
    onConsultar: () -> Unit,
    onSolicitarLimpiar: () -> Unit
) {

    var menuAbierto by remember {
        mutableStateOf(false)
    }

    var mostrarAcercaDe by remember {
        mutableStateOf(false)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
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
        Text(
            text = "Consulta el tiempo de cualquier localidad"
        )
        Spacer(
            modifier = Modifier.height(24.dp)
        )
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
            Button(onClick = onConsultar) {
                Text("Consultar")
            }
            Spacer(
                modifier = Modifier.width(12.dp)
            )
            Button(onClick = onSolicitarLimpiar) {
                Text("Limpiar")
            }
        }
        Spacer(
            modifier = Modifier.width(20.dp)
        )
        Text(
            text = mensaje
        )
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