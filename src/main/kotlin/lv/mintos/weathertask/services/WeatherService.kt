package lv.mintos.weathertask.services

import lv.mintos.weathertask.services.api.GeoLocationApiClient
import lv.mintos.weathertask.services.api.OpenWeatherMapApiClient
import lv.mintos.weathertask.services.api.WhatIsMyIpApiClient
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import javax.servlet.http.HttpServletRequest

@Service
class WeatherService(
        private val ipAddress: IpAddressResolver,
        private val geolocationData: GeoLocationApiClient,
        private val openWeatherApi: OpenWeatherMapApiClient
) {
    private val log = LoggerFactory.getLogger(WeatherService::class.java)

    fun getFor(request: HttpServletRequest): Any {
        log.info("Executing get weather by IP address operation.")
        val ip = ipAddress.of(request)
        val location = geolocationData.getFor(ip)
        return openWeatherApi.getForecastFor(
                countryCode = location.countryCode,
                city = location.city
        )
    }
}