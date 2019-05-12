package lv.mintos.weathertask.domain.dao

import lv.mintos.weathertask.domain.model.ClientRequest
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface ClientRequestDao : CrudRepository<ClientRequest, Long>