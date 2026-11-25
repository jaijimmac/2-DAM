package meteolocal.network.ModelosMeteo

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MeteoCliente {
    object OpenMeteoClient {
        val meteoApi: MeteoApi by lazy {
            Retrofit.Builder()
                .baseUrl("https://api.open-meteo.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(MeteoApi::class.java)
        }
        val geocodingApi: GeocodingApi by lazy {
            Retrofit.Builder()
                .baseUrl("https://geocoding-api.open-meteo.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(GeocodingApi::class.java)
        }
    }
}