package lv.mintos.weathertask.domain.model

import java.time.LocalDateTime
import javax.persistence.Column
import javax.persistence.Embeddable
import javax.persistence.Entity
import javax.persistence.Id

@Embeddable
data class GeoLocation(
        @Column val city: String,
        @Column val countryCode: String
)