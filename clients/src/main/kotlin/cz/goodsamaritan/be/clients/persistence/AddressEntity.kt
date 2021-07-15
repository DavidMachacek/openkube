package cz.goodsamaritan.be.clients.persistence

import cz.goodsamaritan.be.clients.model.AddressDTO
import cz.goodsamaritan.be.clients.model.ClientDTO
import org.hibernate.annotations.CreationTimestamp
import java.time.ZonedDateTime
import javax.persistence.*

@Table(name = "address")
@Entity
data class AddressEntity(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long?,
    val clientsId: Long,
    val street: String,
    val city: String,
    val zipCode: String,
    @CreationTimestamp
    val created: ZonedDateTime?
) {

    fun toDto(): AddressDTO {
        return AddressDTO(
                id = id,
                clientsId = clientsId,
                street = street,
                city = city,
                zipCode = zipCode,
                created = created
        )
    }
}
