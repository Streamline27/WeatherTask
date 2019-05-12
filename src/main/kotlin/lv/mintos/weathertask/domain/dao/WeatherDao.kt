package lv.mintos.weathertask.domain.dao

import lv.mintos.weathertask.domain.model.ClientRequest
import lv.mintos.weathertask.domain.model.Weather
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface WeatherDao : CrudRepository<Weather, Long>