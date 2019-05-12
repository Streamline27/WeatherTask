package lv.mintos.weathertask

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class WeatherTaskApplication

fun main(args: Array<String>) {
	runApplication<WeatherTaskApplication>(*args)
}
