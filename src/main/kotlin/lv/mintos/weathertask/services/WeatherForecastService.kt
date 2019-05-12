package lv.mintos.weathertask.services

import lv.mintos.weathertask.domain.dao.WeatherDao
import lv.mintos.weathertask.domain.model.GeoLocation
import lv.mintos.weathertask.domain.model.Weather
import lv.mintos.weathertask.dto.OpenWeatherDTO
import lv.mintos.weathertask.services.clients.OpenWeatherMapApiClient
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service

@Service
class WeatherForecastService(
        private val openWeatherApi: OpenWeatherMapApiClient,
        private val weatherDao: WeatherDao
) {
    @Cacheable("weathers")
    fun getFor(location: GeoLocation): OpenWeatherDTO {
        val weatherDto = openWeatherApi.getForecastFor(location)
        storeForLaterReference(weatherDto, location)
        return weatherDto
    }

    private fun storeForLaterReference(weatherDto: OpenWeatherDTO, location: GeoLocation) {
        val weather = weatherFor(weatherDto, location)
        weatherDao.save(weather)
    }

    private fun weatherFor(dto: OpenWeatherDTO, location: GeoLocation) = Weather(
            label = dto.weather.first().main,
            description = dto.weather.first().description,
            humidity = dto.main.humidity,
            pressure = dto.main.pressure,
            temperature = dto.main.temp,
            temperatureMax = dto.main.tempMax,
            temperatureMin = dto.main.tempMin,
            windDegree = dto.wind.degree,
            windSpeed = dto.wind.speed,
            location = location
    )
}