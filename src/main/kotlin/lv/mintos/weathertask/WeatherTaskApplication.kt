package lv.mintos.weathertask

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cache.annotation.EnableCaching

@EnableCaching
@SpringBootApplication
class WeatherTaskApplication

fun main(args: Array<String>) {
	runApplication<WeatherTaskApplication>(*args)
}
