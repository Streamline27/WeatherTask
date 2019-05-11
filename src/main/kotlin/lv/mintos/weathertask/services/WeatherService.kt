package lv.mintos.weathertask.services

import lv.mintos.weathertask.services.api.GeoLocationApiClient
import lv.mintos.weathertask.services.api.OpenWeatherMapApiClient
import org.springframework.stereotype.Service
import javax.servlet.http.HttpServletRequest

@Service
class WeatherService(
        private val ipAddress: IpAddressResolver,
        private val geolocationData: GeoLocationApiClient,
        private val openWeatherApi: OpenWeatherMapApiClient
) {
    fun getFor(request: HttpServletRequest): Any {
        val ip = ipAddress.of(request)
        val location = geolocationData.getFor(ip)
        return openWeatherApi.getForecastFor(
                countryCode = location.countryCode,
                city = location.city
        )
    }
}