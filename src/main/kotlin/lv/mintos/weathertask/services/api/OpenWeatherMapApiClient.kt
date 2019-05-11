package lv.mintos.weathertask.services.api

import lv.mintos.weathertask.Properties
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate

@Service
class OpenWeatherMapApiClient(
        private val properties: Properties,
        private val restTemplate: RestTemplate
) {
    private val log = LoggerFactory.getLogger(OpenWeatherMapApiClient::class.java)

    private val urlTemplate = "http://api.openweathermap.org/data/2.5/weather?q={city},{countryCode}&appid={appid}"

    fun getForecastFor(city: String, countryCode: String): Any {
        val params = mapOf(
                "appid" to properties.openweathermapApplicationKey,
                "countryCode" to countryCode.toLowerCase(),
                "city" to city
        )
        log.info("Requesting weather data for city:[$city], countryCode:[$countryCode]")
        return restTemplate.getForObject(urlTemplate, Any::class.java, params)!!
    }
}