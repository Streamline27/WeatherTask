package lv.mintos.weathertask.services.clients

import lv.mintos.weathertask.exceptions.ServiceUnavailableException
import org.slf4j.LoggerFactory
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate

@Service
class WhatIsMyIpApiClient(
        private val restTemplate: RestTemplate
) {
    private val log = LoggerFactory.getLogger(WhatIsMyIpApiClient::class.java)

    private val url = "http://bot.whatismyipaddress.com/"

    @Cacheable("machineIp")
    fun getMachinePublicIp(): String {
        log.info("Requesting local machine ip address.")
        try {
            val entity = restTemplate.getForEntity(url, String::class.java)
            if (entity.statusCode.is2xxSuccessful) return entity.body!!
        } catch (e: Exception) {
            log.error("Unexpected error calling url:[$url]", e)
        }
        throw ServiceUnavailableException(url)
    }
}