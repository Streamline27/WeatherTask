package lv.mintos.weathertask.domain.model

import javax.persistence.*

@Entity
@Table(name = "WEATHER")
data class Weather(
        @Id
        @SequenceGenerator(
                name = "seq_weather",
                sequenceName = "seq_weather",
                allocationSize = 1
        )
        @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_weather")
        @Column val id: Long? = -1,

        @Column val label: String,
        @Column val description: String,
        @Column val temperature: Double,
        @Column val temperatureMin: Double,
        @Column val temperatureMax: Double,
        @Column val pressure: Double,
        @Column val humidity: Double,

        @Column val windSpeed: Double,
        @Column val windDegree: Double,

        @Embedded
        val location: GeoLocation
)