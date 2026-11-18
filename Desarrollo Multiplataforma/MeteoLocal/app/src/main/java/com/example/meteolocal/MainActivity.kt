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
import androidx.compose.runtime.rememberCoroutineScope
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
import com.example.meteolocal.network.ModelosMeteo.MeteoCliente
import com.example.meteolocal.network.ModelosMeteo.ResultadoMeteo
import kotlinx.coroutines.launch


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
    var cargando by remember { mutableStateOf(false) }

    var error by remember { mutableStateOf<String?>(null) }

    var resultado by remember {
        mutableStateOf<ResultadoMeteo?>(null)
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

    val scope = rememberCoroutineScope()

    val onConsultar: () -> Unit = {
        val texto = localidad.trim()
        if (texto.isBlank()) {
            error = "Introduce una localidad."
            resultado = null
        } else {
            scope.launch {
                cargando = true
                error = null
                resultado = null
                try {
                    val geo = MeteoCliente.OpenMeteoClient.geocodingApi
                        .buscarLocalidad(texto)
                    val lugar = geo.results?.firstOrNull()
                    if (lugar == null) {
                        error = "No se ha encontrado esa localidad."
                    } else {
                        val meteo = MeteoCliente.OpenMeteoClient.meteoApi
                            .obtenerTiempoActual(
                                latitud = lugar.latitude,
                                longitud = lugar.longitude
                            )
                        resultado = ResultadoMeteo(
                            localidad = lugar.name,
                            pais = lugar.country,
                            temperatura = meteo.current.temperature_2m,
                            sensacion = meteo.current.apparent_temperature,
                            humedad = meteo.current.relative_humidity_2m,
                            codigo = meteo.current.weather_code,
                            viento = meteo.current.wind_speed_10m
                        )
                    }
                } catch (e: java.io.IOException) {
                    error = "No se ha podido conectar con Internet."
                } catch (e: retrofit2.HttpException) {
                    error = "El servidor ha devuelto un error HTTP."
                } catch (e: Exception) {
                    error = "Se ha producido un error inesperado."
                } finally {
                    cargando = false
                }
            }
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
                    onConsultar = onConsultar,
                    onSolicitarLimpiar = {
                        mostrarDialogoLimpiar = true
                    },
                    onUsarUbicacion = onUsarUbicacion,
                    cargando = cargando,
                    error = error,
                    resultado = resultado
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
                        resultado = null
                        error = null
                        cargando = false
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