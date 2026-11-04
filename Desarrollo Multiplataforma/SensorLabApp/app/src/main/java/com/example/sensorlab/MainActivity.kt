package com.example.sensorlab

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.sensorlab.ui.theme.SensorLabTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SensorLabTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    SensorLabApp(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun SensorLabApp(modifier: Modifier = Modifier) {
    var context = LocalContext.current;

    var sensorManager = remember(context) {
        context.getSystemService(Context.SENSOR_SERVICE)
                as SensorManager
    }

    val acelerometro = remember(sensorManager) {
        sensorManager.getDefaultSensor(
            Sensor.TYPE_ACCELEROMETER
        )
    }

    var x by remember { mutableStateOf(0f) }
    var y by remember { mutableStateOf(0f) }
    var z by remember { mutableStateOf(0f) }

    val acelerometroDisponible = acelerometro != null


    val listener = remember {
        object : SensorEventListener {
            override fun onSensorChanged(event: SensorEvent?) {
                if (event?.sensor?.type == Sensor.TYPE_ACCELEROMETER) {
                    x = event.values[0]
                    y = event.values[1]
                    z = event.values[2]
                }
            }

            override fun onAccuracyChanged(
                sensor: Sensor?, accuracy: Int
            ) {

            }
        }
    }

    DisposableEffect(sensorManager, acelerometro, listener) {
        if (acelerometro != null) {
            sensorManager.registerListener(
                listener,
                acelerometro,
                SensorManager.SENSOR_DELAY_NORMAL
            )
        }
        onDispose {
            sensorManager.unregisterListener(listener)
        }
    }


    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            "SensorLab",
            style = MaterialTheme.typography.headlineMedium
        )
        Spacer(modifier = Modifier.height(24.dp))
        if (acelerometroDisponible) {
            Text("Acelerómetro disponible")
            Spacer(modifier = Modifier.height(16.dp))
            Text("X: %.2f".format(x))
            Text("Y: %.2f".format(y))
            Text("Z: %.2f".format(z))
        } else {
            Text("Este dispositivo no dispone de acelerómetro")
        }

    }
}