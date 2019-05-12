package lv.mintos.weathertask.services

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import lv.mintos.weathertask.TestData
import lv.mintos.weathertask.TestData.IP_ADDRESS
import lv.mintos.weathertask.services.WeatherForecastServiceTest.Companion.LOCATION
import lv.mintos.weathertask.services.WeatherForecastServiceTest.Companion.OPEN_WEATHER_DTO
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.TestInstance
import javax.servlet.http.HttpServletRequest

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class WeatherServiceTest {

    private val REQUEST: HttpServletRequest = mockk()

    private val ipAddresses: IpAddressExtractor = mockk()
    private val geolocations: GeoLocationService = mockk()
    private val weatherData: WeatherForecastService = mockk()
    private val weatherService: WeatherService = WeatherService(ipAddresses, geolocations, weatherData)

    @BeforeEach
    fun setUp() {
        every { ipAddresses.getIpBy(REQUEST) } returns IP_ADDRESS
        every { geolocations.getFor(IP_ADDRESS) } returns LOCATION
        every { weatherData.getFor(LOCATION) } returns OPEN_WEATHER_DTO
    }

    @Test
    fun `when request is passed open weather dto is returned`() {
        val weatherDto = weatherService.getFor(REQUEST)
        assertThat(weatherDto).isEqualTo(OPEN_WEATHER_DTO)
    }

    @Test
    fun `when request is passed ip is returned by that request`() {
        verify { ipAddresses.getIpBy(REQUEST) }
        weatherService.getFor(REQUEST)
    }

    @Test
    fun `when request is passed geolocation is returned by ip from IpAddressExtractor`() {
        verify { geolocations.getFor(IP_ADDRESS) }
        weatherService.getFor(REQUEST)
    }

    @Test
    fun `when request is passed weather is returned by geolocation from GeoLocationService`() {
        verify { geolocations.getFor(IP_ADDRESS) }
        weatherService.getFor(REQUEST)
    }
}