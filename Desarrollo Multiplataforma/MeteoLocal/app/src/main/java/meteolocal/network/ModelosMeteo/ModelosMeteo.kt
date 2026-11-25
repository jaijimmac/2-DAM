package meteolocal.network.ModelosMeteo

class ModelosMeteo {
    data class RespuestaMeteo(
        val current: TiempoActual
    )
    data class TiempoActual(
        val temperature_2m: Double,
        val apparent_temperature: Double,
        val relative_humidity_2m: Int,
        val weather_code: Int,
        val wind_speed_10m: Double
    )
}