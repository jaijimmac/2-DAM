package com.example.meteolocal

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.meteolocal.ui.theme.MeteoLocalTheme
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
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

    var mensajeUbicacion by remember {
        mutableStateOf(
            "No se ha solicitado autorización todavía."
        )
    }

    val context = LocalContext.current

    val permissionLauncher =
        rememberLauncherForActivityResult(
            contract =
                ActivityResultContracts.RequestPermission()
        ) { concedido: Boolean ->
            if (concedido) {
                mensajeUbicacion =
                    "Permiso de ubicación concedido."
            } else {
                mensajeUbicacion =
                    "No se ha autorizado la ubicación. " +
                            "Puedes introducir una localidad manualmente."
            }
        }

    val onUsarUbicacion = {
        val permisoConcedido =
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        if (permisoConcedido) {
            mensajeUbicacion =
                "Permiso de ubicación ya concedido."
        } else {
            permissionLauncher.launch(
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
        }
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
                    mensajeUbicacion = mensajeUbicacion,
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
                    },
                    onUsarUbicacion = onUsarUbicacion
                )

            }

            composable("favoritos") {
                FavoritosScreen();
            }

            composable("configuracion") {
                ConfiguracionScreen()
            }
        }
    }
    if (mostrarDialogoLimpiar) {
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
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MeteoLocalTheme {
        MeteoLocalApp()
    }
}