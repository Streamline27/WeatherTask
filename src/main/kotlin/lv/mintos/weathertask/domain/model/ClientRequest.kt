package lv.mintos.weathertask.domain.model

import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "CLIENT_REQUESTS")
data class ClientRequest(
        @Id
        @SequenceGenerator(
                name = "seq_client_requests",
                sequenceName = "seq_client_requests",
                allocationSize = 1
        )
        @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_client_requests")
        @Column val id: Long = -1,

        @Column val time: LocalDateTime,
        @Column val ipAddress: String,

        @Embedded
        val location: GeoLocation
)