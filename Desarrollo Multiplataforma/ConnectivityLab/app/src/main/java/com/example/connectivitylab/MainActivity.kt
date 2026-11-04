package com.example.connectivitylab

import android.content.Context
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.connectivitylab.ui.theme.ConnectivityLabTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ConnectivityLabTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    ConnectivityLabApp(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun ConnectivityLabApp(modifier: Modifier = Modifier) {

    val context = LocalContext.current;
    val packageManager = context.packageManager;

    val wifiDisponible = packageManager.hasSystemFeature(
        PackageManager.FEATURE_WIFI
    )
    val bluetoothDisponible = packageManager.hasSystemFeature(
        PackageManager.FEATURE_BLUETOOTH
    )
    val bluetoothLeDisponible = packageManager.hasSystemFeature(
        PackageManager.FEATURE_BLUETOOTH_LE
    )
    val nfcDisponible = packageManager.hasSystemFeature(
        PackageManager.FEATURE_NFC
    )

    val connectivityManager = context.getSystemService(
        Context.CONNECTIVITY_SERVICE
    ) as ConnectivityManager

    val redActiva = connectivityManager.activeNetwork
    val capacidades = connectivityManager.getNetworkCapabilities(redActiva)

    val tipoConexion = when {
        capacidades == null -> "Sin conectividad"
        capacidades.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> "Wi-Fi"
        capacidades.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> "Red móvil"
        capacidades.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> "Ethernet"
        else -> "Otro tipo de red"
    }

    val tieneInternet = capacidades?.hasCapability(
        NetworkCapabilities.NET_CAPABILITY_INTERNET
    ) == true

    fun siNo(valor: Boolean): String =
        if (valor) "Sí" else "No"

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(24.dp)
    ) {
        Text(
            text = "ConnectivityLab",
            style = MaterialTheme.typography.headlineMedium
        )
        Spacer(modifier = Modifier.height(24.dp))
        Text("Wi-Fi disponible: ${siNo(wifiDisponible)}")
        Text("Bluetooth disponible: ${siNo(bluetoothDisponible)}")
        Text("Bluetooth LE disponible: ${siNo(bluetoothLeDisponible)}")
        Text("NFC disponible: ${siNo(nfcDisponible)}")
        Spacer(modifier = Modifier.height(24.dp))
        Text("Conectividad activa:")
        Text(tipoConexion)
        Spacer(modifier = Modifier.height(16.dp))
        Text("Red con capacidad de Internet: ${siNo(tieneInternet)}")
    }
}

