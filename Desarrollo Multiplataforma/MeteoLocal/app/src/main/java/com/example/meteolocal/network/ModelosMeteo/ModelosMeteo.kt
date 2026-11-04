package com.example.meteolocal.network.ModelosMeteo

class ModelosMeteo {
    data class RespuestaMeteo(
        val current: TiempoActual
    )
    data class TiempoActual(
        val temperature_2m: Double
    )

}