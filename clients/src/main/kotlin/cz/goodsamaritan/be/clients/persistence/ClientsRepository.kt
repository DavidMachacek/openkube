package cz.goodsamaritan.be.clients.persistence

import org.springframework.data.jpa.repository.JpaRepository

interface ClientsRepository: JpaRepository<ClientEntity, Long> {
}