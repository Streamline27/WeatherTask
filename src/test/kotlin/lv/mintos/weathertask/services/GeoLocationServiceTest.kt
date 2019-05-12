package lv.mintos.weathertask.services

import io.mockk.*
import lv.mintos.weathertask.TestData.CITY
import lv.mintos.weathertask.TestData.CLIENT_REQUEST_SAVED
import lv.mintos.weathertask.TestData.COUNTRY_CODE
import lv.mintos.weathertask.TestData.GEO_LOCATION_DTO
import lv.mintos.weathertask.TestData.IP_ADDRESS
import lv.mintos.weathertask.domain.dao.ClientRequestDao
import lv.mintos.weathertask.domain.model.ClientRequest
import lv.mintos.weathertask.services.clients.GeoLocationApiClient
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class GeoLocationServiceTest {

    private val geolocationApi: GeoLocationApiClient = mockk()
    private val clientRequestDao: ClientRequestDao = mockk()
    private val geoLocationService: GeoLocationService = GeoLocationService(geolocationApi, clientRequestDao)

    @BeforeEach
    fun setUp() {
        every { geolocationApi.getFor(IP_ADDRESS) } returns GEO_LOCATION_DTO
        every { clientRequestDao.save(any<ClientRequest>()) } returns CLIENT_REQUEST_SAVED
    }

    @Test
    fun `geolocation api called with ip that was passed as parameter`() {
        geoLocationService.getFor(IP_ADDRESS)
        verify { geolocationApi.getFor(IP_ADDRESS) }
    }

    @Test
    fun `geo location field city is assigned from dto city field`() {
        every { geolocationApi.getFor(IP_ADDRESS) } returns GEO_LOCATION_DTO
        val location = geoLocationService.getFor(IP_ADDRESS)
        assertThat(location.city).isEqualTo(CITY)
    }

    @Test
    fun `countryCode is assigned from dto countryCode field`() {
        every { geolocationApi.getFor(IP_ADDRESS) } returns GEO_LOCATION_DTO
        val location = geoLocationService.getFor(IP_ADDRESS)
        assertThat(location.countryCode).isEqualTo(COUNTRY_CODE)
    }

    @Test
    fun `client request field countryCode is assigned from dto countryCode field`() {
        val actualClientRequest = slot<ClientRequest>()
        every { geolocationApi.getFor(IP_ADDRESS) } returns GEO_LOCATION_DTO
        every { clientRequestDao.save(capture(actualClientRequest)) } returns CLIENT_REQUEST_SAVED

        geoLocationService.getFor(IP_ADDRESS)
        assertThat(actualClientRequest.captured.location.countryCode).isEqualTo(COUNTRY_CODE)
    }

    @Test
    fun `client request field city is assigned from dto city field`() {
        val actualClientRequest = slot<ClientRequest>()
        every { geolocationApi.getFor(IP_ADDRESS) } returns GEO_LOCATION_DTO
        every { clientRequestDao.save(capture(actualClientRequest)) } returns CLIENT_REQUEST_SAVED

        geoLocationService.getFor(IP_ADDRESS)
        assertThat(actualClientRequest.captured.location.city).isEqualTo(CITY)
    }

    @Test
    fun `client request field isAdress is assigned from parameter isAddress`() {
        val actualClientRequest = slot<ClientRequest>()
        every { clientRequestDao.save(capture(actualClientRequest)) } returns CLIENT_REQUEST_SAVED

        geoLocationService.getFor(IP_ADDRESS)
        assertThat(actualClientRequest.captured.ipAddress).isEqualTo(IP_ADDRESS)
    }
}