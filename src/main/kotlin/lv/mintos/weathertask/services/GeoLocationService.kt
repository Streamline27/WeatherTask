package lv.mintos.weathertask.services

import lv.mintos.weathertask.domain.dao.ClientRequestDao
import lv.mintos.weathertask.domain.model.ClientRequest
import lv.mintos.weathertask.domain.model.GeoLocation
import lv.mintos.weathertask.dto.GeoLocationDTO
import lv.mintos.weathertask.services.clients.GeoLocationApiClient
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class GeoLocationService(
        private val geolocationApi: GeoLocationApiClient,
        private val clientRequestDao: ClientRequestDao
) {
    fun getFor(ipAddress: String): GeoLocation {
        val locationDto = geolocationApi.getFor(ipAddress)
        val location = internalFormOf(locationDto)
        val clientRequest = clientRequestFor(location, ipAddress)
        clientRequestDao.save(clientRequest)
        return location
    }

    private fun internalFormOf(locationDto: GeoLocationDTO) = GeoLocation(
            city = locationDto.city,
            countryCode = locationDto.countryCode
    )

    private fun clientRequestFor(location: GeoLocation, ipAddress: String) = ClientRequest(
            time = LocalDateTime.now(),
            ipAddress = ipAddress,
            location = location
    )
}