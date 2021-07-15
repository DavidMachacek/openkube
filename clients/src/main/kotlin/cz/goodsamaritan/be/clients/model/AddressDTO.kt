package cz.goodsamaritan.be.clients.model

import cz.goodsamaritan.be.clients.persistence.AddressEntity
import java.time.ZonedDateTime

data class AddressDTO(
        val id: Long? = null,
        val clientsId: Long,
        val street: String,
        val city: String,
        val created: ZonedDateTime? = null,
        val zipCode: String)
{
    fun toEntity(clientsId: Long): AddressEntity {
        return AddressEntity(
                id = id,
                clientsId = clientsId,
                street = street,
                created = created,
                zipCode = zipCode,
                city = city
        )
    }
}