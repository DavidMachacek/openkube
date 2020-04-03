package cz.goodsamaritan.be.queries.facade

import cz.goodsamaritan.be.queries.model.ClientDTO
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.HttpMethod
import org.springframework.stereotype.Service
import org.springframework.web.client.RestClientException
import org.springframework.web.client.RestTemplate

@Service
class ClientsFacade(
        private val clientsRestTemplate: RestTemplate
) {

    companion object {
        const val ROOT_PATH = "/clients"
        inline fun <reified T> typeReference() = object : ParameterizedTypeReference<T>() {}
    }

    fun getAll(): List<ClientDTO> {
        return clientsRestTemplate.exchange(ROOT_PATH, HttpMethod.GET, null, typeReference<List<ClientDTO>>()).body ?: emptyList()
    }

    fun getOne(id: Long): ClientDTO {
        return clientsRestTemplate.getForObject(ROOT_PATH, ClientDTO::class.java, id) ?:
            throw RestClientException("Client with id $id not found!")
    }

    fun saveNew(client: ClientDTO) {
        clientsRestTemplate.postForObject(ROOT_PATH, client, Void::class.java)
    }
}