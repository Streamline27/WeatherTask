package lv.mintos.weathertask.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class OpenWeatherDTO(
        val weather: List<WeatherDTO>,
        val main: MainDTO,
        val wind: WindDTO
) {
    data class WeatherDTO(
            val main: String,
            val description: String
    )
    data class MainDTO(
            val temp: Double,
            val pressure: Double,
            val humidity: Double,
            @JsonProperty("temp_min") val tempMin: Double,
            @JsonProperty("temp_max") val tempMax: Double
    )
    data class WindDTO(
            val speed: Double,
            @JsonProperty("deg") val degree: Double
    )
}