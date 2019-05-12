package lv.mintos.weathertask

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import org.springframework.cache.annotation.EnableCaching
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.client.RestTemplate

@EnableCaching
@Configuration
class Config {
    @Bean fun restTemplate() = RestTemplate()
    @Bean fun mapper() = ObjectMapper().registerModule(KotlinModule())
}