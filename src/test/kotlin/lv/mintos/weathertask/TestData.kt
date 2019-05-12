package lv.mintos.weathertask

import lv.mintos.weathertask.domain.model.ClientRequest
import lv.mintos.weathertask.domain.model.GeoLocation
import lv.mintos.weathertask.domain.model.Weather
import lv.mintos.weathertask.dto.GeoLocationDTO
import lv.mintos.weathertask.dto.OpenWeatherDTO
import java.time.LocalDateTime

object TestData {

    const val IP_ADDRESS = "192.168.0.11"
    const val IP_ADDRESS_LOCALHOST = "0:0:0:0:0:0:0:1"

    const val COUNTRY_CODE = "LV"
    const val CITY = "RIGA"

    const val LABEL = "label"
    const val DESCRIPTION = "description"
    const val TEMP = 10.0
    const val HUMIDITY = 20.0
    const val TEMP_MIN = 30.0
    const val TEMP_MAX = 40.0
    const val PRESSURE = 50.0
    const val SPEED = 1.0
    const val DEGREE = 2.0

    val LOCATION = GeoLocation(countryCode = COUNTRY_CODE, city = CITY)
    val OPEN_WEATHER_DTO = OpenWeatherDTO(
            weather = listOf(OpenWeatherDTO.WeatherDTO(
                    main = LABEL,
                    description = DESCRIPTION
            )),
            main = OpenWeatherDTO.MainDTO(
                    temp = TEMP,
                    humidity = HUMIDITY,
                    tempMin = TEMP_MIN,
                    tempMax = TEMP_MAX,
                    pressure = PRESSURE
            ),
            wind = OpenWeatherDTO.WindDTO(
                    speed = SPEED,
                    degree = DEGREE
            )
    )
    val WEATHER = Weather(
            label = LABEL,
            temperatureMin = TEMP_MIN,
            temperatureMax = TEMP_MAX,
            temperature = TEMP,
            pressure = PRESSURE,
            humidity = HUMIDITY,
            description = DESCRIPTION,
            location = LOCATION,
            windSpeed = SPEED,
            windDegree = DEGREE
    )
    val GEO_LOCATION_DTO = GeoLocationDTO(city = CITY, countryCode = COUNTRY_CODE)

    val CLIENT_REQUEST_SAVED = ClientRequest(
            id = 1,
            time = LocalDateTime.now(),
            ipAddress = IP_ADDRESS,
            location = GeoLocation(city = CITY, countryCode = COUNTRY_CODE)
    )
}