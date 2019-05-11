package lv.mintos.weathertask.services.api

import lv.mintos.weathertask.dto.GeoLocationDTO
import lv.mintos.weathertask.services.IpAddressResolver
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate

@Service
class GeoLocationApiClient(
        private val restTemplate: RestTemplate
) {
    private val log = LoggerFactory.getLogger(IpAddressResolver::class.java)

    private val urlTemplate = "http://ip-api.com/json/{ipAddress}"

    fun getFor(ipAddress: String) : GeoLocationDTO {
        val params = mapOf("ipAddress" to ipAddress)
        log.info("Requesting geolocation data for ipAddress:[$ipAddress]")
        return restTemplate.getForObject(urlTemplate, GeoLocationDTO::class.java, params)!!
    }
}