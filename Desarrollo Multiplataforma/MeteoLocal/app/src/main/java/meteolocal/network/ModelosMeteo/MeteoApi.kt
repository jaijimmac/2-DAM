package meteolocal.network.ModelosMeteo

import retrofit2.http.GET
import retrofit2.http.Query
interface MeteoApi {
    @GET("v1/forecast")
    suspend fun obtenerTiempoActual(
        @Query("latitude") latitud: Double,
        @Query("longitude") longitud: Double,
        @Query("current") variables: String =
            "temperature_2m,apparent_temperature," +
                    "relative_humidity_2m,weather_code," +
                    "wind_speed_10m"
    ): ModelosMeteo.RespuestaMeteo
}