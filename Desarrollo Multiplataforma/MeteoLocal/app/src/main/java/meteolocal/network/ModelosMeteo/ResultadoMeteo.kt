package meteolocal.network.ModelosMeteo


data class ResultadoMeteo(
    val localidad: String,
    val pais: String?,
    val temperatura: Double,
    val sensacion: Double,
    val humedad: Int,
    val codigo: Int,
    val viento: Double,
    val latitud: Double,
    val longitud: Double
)