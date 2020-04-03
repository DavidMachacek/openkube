package cz.goodsamaritan.be.queries.model

import java.time.ZonedDateTime

data class ClientDTO (
        val id: Int? = null,
        val firstName: String,
        val lastName: String,
        val created: ZonedDateTime? = null
)