package lv.mintos.weathertask

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

@Service
class Properties(
        @Value("\${custom.openweathermap.application-key}") val openweathermapApplicationKey: String
)