package cz.goodsamaritan.be.clients.persistence

import org.springframework.data.jpa.repository.JpaRepository

interface AddressRepository: JpaRepository<AddressEntity, Long> {

    fun findDistinctById(id: Long): AddressEntity

    fun findAllByClientsId(id: Long): List<AddressEntity>
}