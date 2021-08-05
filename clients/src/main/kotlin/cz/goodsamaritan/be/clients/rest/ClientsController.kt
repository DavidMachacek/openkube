package cz.goodsamaritan.be.clients.rest

import cz.goodsamaritan.be.clients.model.AddressDTO
import cz.goodsamaritan.be.clients.model.ClientDTO
import cz.goodsamaritan.be.clients.model.ClientLoginDTO
import cz.goodsamaritan.be.clients.persistence.AddressRepository
import cz.goodsamaritan.be.clients.persistence.ClientsRepository
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import org.springframework.web.client.HttpClientErrorException

@RestController
@RequestMapping("/clients")
class ClientsController(
        private val clientsRepository: ClientsRepository,
        private val addressRepository: AddressRepository)
{
    val logger: Logger = LoggerFactory.getLogger(ClientsController::class.java)

    @GetMapping
    fun getAll(): List<ClientDTO> {
        logger.info("request=[REST]/clients, action=getAllClientsBegin")
        return clientsRepository.findAll().map { it.toDto() }.also {
            logger.info("action=getAllClientsEnd")
        }
    }

    @GetMapping("/{id}")
    fun getOne(@PathVariable id: Long): ClientDTO {
        logger.info("request=[REST]/clients/{id}, action=getOneClientBegin, params=[id=$id]")
        return clientsRepository.findById(id).get().toDto().also {
            logger.info("action=getOneClientEnd")
        }
    }

    @PostMapping
    fun create(@RequestBody client: ClientDTO): ClientDTO {
        logger.info("request=[REST]/clients, action=saveNewClientBegin")
        return clientsRepository.save(client.toEntity()).toDto().also {
            logger.info("action=saveNewClientEnd")
        }
    }

    @PostMapping("/login")
    fun login(@RequestBody client: ClientLoginDTO): ClientDTO {
        logger.info("request=[REST]/clients/login, action=loginBegin")
        return clientsRepository.findDistinctByUsername(client.username).toDto()
                .takeIf { it.password == client.password } ?: throw HttpClientErrorException(HttpStatus.UNAUTHORIZED, "Sorry, bad password mate!")
                .also {
            logger.info("action=loginEnd")
        }
    }

    @GetMapping("/{id}/address")
    fun getAddress(@PathVariable id: Long): List<AddressDTO> {
        logger.info("request=[REST]/clients/{id}/address, action=getClientsAddressBegin, params=[id=$id]")
        return addressRepository.findAllByClientsId(id).map { it -> it.toDto() }.also {
            logger.info("action=getClientsAddressEnd")
        }
    }
    @PostMapping("/{id}/address")
    fun postAddress(@PathVariable id: Long, @RequestBody address: AddressDTO): AddressDTO {
        logger.info("request=[REST]/clients/{id}/address, action=postClientsAddressBegin, params=[id=$id]")
        return addressRepository.save(address.toEntity(id)).toDto().also {
            logger.info("action=postClientsAddressEnd")
        }
    }
}