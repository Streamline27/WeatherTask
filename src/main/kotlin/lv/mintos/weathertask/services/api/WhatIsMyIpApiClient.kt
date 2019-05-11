package lv.mintos.weathertask.services.api

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate

@Service
class WhatIsMyIpApiClient(
        private val restTemplate: RestTemplate
) {
    private val log = LoggerFactory.getLogger(WhatIsMyIpApiClient::class.java)

    private val url = "http://bot.whatismyipaddress.com/"

    fun getMachinePublicIp(): String {
        log.info("Requesting local machine ip address.")
        return restTemplate.getForObject(url, String::class.java)!!
    }
}