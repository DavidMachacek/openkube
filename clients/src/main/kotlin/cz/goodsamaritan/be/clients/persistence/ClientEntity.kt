package cz.goodsamaritan.be.clients.persistence

import cz.goodsamaritan.be.clients.model.ClientDTO
import org.hibernate.annotations.CreationTimestamp
import java.time.ZonedDateTime
import javax.persistence.*

@Table(name = "clients")
@Entity
data class ClientEntity(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long?,
    val firstName: String,
    val lastName: String,
    @CreationTimestamp
    val created: ZonedDateTime?
) {

    fun toDto(): ClientDTO {
        return ClientDTO(
                id = id,
                firstName = firstName,
                lastName = lastName,
                created = created
        )
    }
}
