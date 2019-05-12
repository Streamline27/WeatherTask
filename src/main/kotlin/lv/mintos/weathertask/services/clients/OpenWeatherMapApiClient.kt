package lv.mintos.weathertask.services.clients

import lv.mintos.weathertask.Properties
import lv.mintos.weathertask.domain.model.GeoLocation
import lv.mintos.weathertask.dto.OpenWeatherDTO
import lv.mintos.weathertask.exceptions.ServiceUnavailableException
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import org.springframework.web.util.UriComponentsBuilder

@Service
class OpenWeatherMapApiClient(
        private val properties: Properties,
        private val restTemplate: RestTemplate
) {
    private val log = LoggerFactory.getLogger(OpenWeatherMapApiClient::class.java)

    private val urlTemplate = "http://api.openweathermap.org/data/2.5/weather?q={city},{countryCode}&appid={appid}&units=metric"

    fun getForecastFor(location: GeoLocation): OpenWeatherDTO = with(location) {
        log.info("Requesting weather data for city:[$city], countryCode:[$countryCode]")
        val url = getUrl(countryCode = countryCode, city = city)
        try {
            val response = restTemplate.getForEntity(url, OpenWeatherDTO::class.java)
            if (response.statusCode.is2xxSuccessful) return response.body!!
        } catch (e: Exception) {
            log.error("Unexpected error calling url:[$url]", e)
        }
        throw ServiceUnavailableException(url)
    }

    private fun getUrl(countryCode: String, city: String): String {
        val params = mapOf(
                "appid" to properties.openweathermapApplicationKey,
                "countryCode" to countryCode.toLowerCase(),
                "city" to city
        )
        return UriComponentsBuilder.fromUriString(urlTemplate)
                .buildAndExpand(params)
                .toUriString()
    }
}