package meteolocal.network.ModelosMeteo

import retrofit2.http.GET
import retrofit2.http.Query


interface GeocodingApi {
    @GET("v1/search")
    suspend fun buscarLocalidad(
        @Query("name") nombre: String,
        @Query("count") count: Int = 1,
        @Query("language") language: String = "es",
        @Query("format") format: String = "json"
    ): ModelosGeocodificacion.RespuestaGeocodificacion
}