package cz.goodsamaritan.be.queries.model

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository


@Repository
interface ClientRepository: CrudRepository<ClientDTO, Long> {
}