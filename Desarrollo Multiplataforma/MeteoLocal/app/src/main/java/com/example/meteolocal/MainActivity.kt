package com.example.meteolocal

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.meteolocal.ui.theme.MeteoLocalTheme
import androidx.compose.runtime.setValue
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MeteoLocalTheme {
                MeteoLocalApp()
            }
        }
    }
}

@Composable
fun MeteoLocalApp(){

    var localidad by remember { mutableStateOf("") }

    var mensaje by remember {
        mutableStateOf("Introduce una localidad")
    }

    var mostrarDialogoLimpiar by remember {
        mutableStateOf(false)
    }

    val navController = rememberNavController()

    val backStackEntry by
    navController.currentBackStackEntryAsState()

    val rutaActual =
        backStackEntry?.destination?.route

    Scaffold(
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    selected = rutaActual == "consulta",
                    onClick = {
                        navController.navigate("consulta")
                    },
                    icon = { Text("C") },
                    label = { Text("Consulta") }
                )

                NavigationBarItem(
                    selected = rutaActual == "favoritos",
                    onClick = {
                        navController.navigate("favoritos")
                    },
                    icon = { Text("F") },
                    label = { Text("Favoritos") }
                )

                NavigationBarItem(
                    selected = rutaActual == "configuracion",
                    onClick = {
                        navController.navigate("configuracion")
                    },
                    icon = { Text("A") },
                    label = { Text("Ajustes") }
                )
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "consulta",
            modifier = Modifier.padding(innerPadding)
        ) {
            composable("consulta") {
                ConsultaScreen(
                    localidad = localidad,
                    mensaje = mensaje,
                    onLocalidadChange = { nuevoValor: String ->
                        localidad = nuevoValor
                    },
                    onConsultar = {
                        mensaje =
                            if (localidad.isBlank()) {
                                "Introduce una localidad"
                            } else {
                                "Consulta preparada para: $localidad"
                            }
                    },
                    onSolicitarLimpiar = {
                        mostrarDialogoLimpiar = true
                    }
                )
            }

            composable("favoritos") {
                FavoritosScreen()
            }

            composable("configuracion") {
                ConfiguracionScreen()
            }
        }
    }
    AlertDialog(
        onDismissRequest = {
            mostrarDialogoLimpiar = false
        },
        title = {
            Text("Limpiar consulta")
        },
        text = {
            Text("¿Deseas borrar la localidad introducida?")
        },
        confirmButton = {
            TextButton(
                onClick = {
                    localidad = ""
                    mensaje = "Introduce una localidad"
                    mostrarDialogoLimpiar = false
                }
            ) {
                Text("Sí, limpiar")
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    mostrarDialogoLimpiar = false
                }
            ) {
                Text("Cancelar")
            }
        }
    )
}


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
        Text(
            text = "MeteoLocal",
            style = MaterialTheme.typography.headlineMedium
        )
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

@Composable
fun FavoritosScreen() {
    Text("Todavía no tienes localidades favoritas")
}



















@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MeteoLocalTheme {
        MeteoLocalApp()
    }
}