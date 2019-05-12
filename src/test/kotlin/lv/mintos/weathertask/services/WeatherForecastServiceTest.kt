package lv.mintos.weathertask.services

import io.mockk.*
import lv.mintos.weathertask.TestData
import lv.mintos.weathertask.domain.dao.WeatherDao
import lv.mintos.weathertask.domain.model.Weather
import lv.mintos.weathertask.services.clients.OpenWeatherMapApiClient
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream
import kotlin.reflect.KProperty

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class WeatherForecastServiceTest {

    companion object {
        val LOCATION = TestData.LOCATION
        val OPEN_WEATHER_DTO = TestData.OPEN_WEATHER_DTO
        val WEATHER = TestData.WEATHER
        val WEATHER_SAVED = WEATHER.copy(id = 1)
    }

    private val openWeatherApi: OpenWeatherMapApiClient = mockk()
    private val weatherDao: WeatherDao = mockk()
    private val weatherForecastService: WeatherForecastService = WeatherForecastService(openWeatherApi, weatherDao)

    @BeforeEach
    fun setUp() {
        every { openWeatherApi.getForecastFor(LOCATION) } returns OPEN_WEATHER_DTO
        every { weatherDao.save(any<Weather>()) } returns WEATHER_SAVED
    }

    @Test
    fun `when forescast is received data is equal to passed dto`() {
        val forecast = weatherForecastService.getFor(LOCATION)
        assertThat(forecast).isEqualTo(OPEN_WEATHER_DTO)
    }

    @ParameterizedTest
    @MethodSource("weatherExpectedFields")
    fun `saved weather field is equal to weatherDto field`(expectedField: ExpectedField) {
        val savedWeatherCaptor = slot<Weather>()
        every { weatherDao.save(capture(savedWeatherCaptor)) } returns WEATHER_SAVED
        weatherForecastService.getFor(LOCATION)

        val savedWeather = savedWeatherCaptor.captured
        val actualFieldValue = expectedField.property.call(savedWeather)
        assertThat(actualFieldValue).isEqualTo(expectedField.value)
    }

    private fun weatherExpectedFields() = Stream.of(
            ExpectedField(value = TestData.LABEL, property = Weather::label),
            ExpectedField(value = TestData.TEMP_MIN, property = Weather::temperatureMin),
            ExpectedField(value = TestData.TEMP_MAX, property = Weather::temperatureMax),
            ExpectedField(value = TestData.TEMP, property = Weather::temperature),
            ExpectedField(value = TestData.PRESSURE, property = Weather::pressure),
            ExpectedField(value = TestData.HUMIDITY, property = Weather::humidity),
            ExpectedField(value = TestData.SPEED, property = Weather::windSpeed),
            ExpectedField(value = TestData.DEGREE, property = Weather::windDegree),
            ExpectedField(value = TestData.LOCATION, property = Weather::location),
            ExpectedField(value = TestData.DESCRIPTION, property = Weather::description)
    )

    data class ExpectedField(
            val property: KProperty<*>,
            val value: Any
    )
}