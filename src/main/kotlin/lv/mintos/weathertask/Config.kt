package lv.mintos.weathertask

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.github.benmanes.caffeine.cache.Caffeine
import com.github.benmanes.caffeine.cache.Ticker
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.client.RestTemplate
import java.util.concurrent.TimeUnit

@Configuration
class Config {
    @Bean fun restTemplate() = RestTemplate()
    @Bean fun mapper() = ObjectMapper().registerModule(KotlinModule())
}