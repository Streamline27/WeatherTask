package lv.mintos.weathertask.services

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import javax.servlet.http.HttpServletRequest
import javax.transaction.Transactional

@Service
class WeatherService(
        private val ipAddresses: IpAddressExtractor,
        private val geolocations: GeoLocationService,
        private val weatherData: WeatherForecastService
) {
    private val log = LoggerFactory.getLogger(WeatherService::class.java)

    @Transactional
    fun getFor(request: HttpServletRequest): Any {
        log.info("Executing get weather by IP address operation.")
        val ip = ipAddresses.getIpBy(request)
        val location = geolocations.getFor(ip)
        return weatherData.getFor(location)
    }
}