package lv.mintos.weathertask.services.clients

import lv.mintos.weathertask.dto.GeoLocationDTO
import lv.mintos.weathertask.exceptions.ServiceUnavailableException
import lv.mintos.weathertask.services.IpAddressExtractor
import org.slf4j.LoggerFactory
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import org.springframework.web.util.UriComponentsBuilder

@Service
class GeoLocationApiClient(
        private val restTemplate: RestTemplate
) {
    private val log = LoggerFactory.getLogger(IpAddressExtractor::class.java)

    private val urlTemplate = "http://ip-api.com/json/{ipAddress}"

    @Cacheable("geoLocations")
    fun getFor(ipAddress: String): GeoLocationDTO {
        log.info("Requesting geolocation data for ipAddress:[$ipAddress]")
        val url = getUrl(ipAddress)
        try {
            val response = restTemplate.getForEntity(url, GeoLocationDTO::class.java)
            if (response.statusCode.is2xxSuccessful) return response.body!!
        } catch (e: Exception) {
            log.error("Unexpected error calling url:[$url]", e)
        }
        throw ServiceUnavailableException(url)
    }

    private fun getUrl(ipAddress: String): String {
        val params = mapOf("ipAddress" to ipAddress)
        return UriComponentsBuilder.fromUriString(urlTemplate)
                .buildAndExpand(params)
                .toUriString()
    }
}