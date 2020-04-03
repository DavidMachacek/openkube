package cz.goodsamaritan.be.clients.model

import cz.goodsamaritan.be.clients.persistence.ClientEntity
import java.time.ZonedDateTime

data class ClientDTO (
        val id: Int? = null,
        val firstName: String,
        val lastName: String,
        val created: ZonedDateTime? = null
) {
    fun toEntity(): ClientEntity {
        return ClientEntity(
                id = id,
                firstName = firstName,
                lastName = lastName,
                created = created
        )
    }
}