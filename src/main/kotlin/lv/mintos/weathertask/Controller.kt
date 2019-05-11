package lv.mintos.weathertask

import lv.mintos.weathertask.services.WeatherService
import org.springframework.web.bind.annotation.*
import javax.servlet.http.HttpServletRequest

@RestController
class Controller(
        private val weather: WeatherService
) {
    @GetMapping("/weather")
    fun weather(request: HttpServletRequest) =
            weather.getFor(request)
}