package cz.goodsamaritan.be.clients.rest

import cz.goodsamaritan.be.clients.model.ClientDTO
import cz.goodsamaritan.be.clients.persistence.ClientsRepository
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/clients")
class ClientsController(
        private val clientsRepository: ClientsRepository)
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
    fun create(client: ClientDTO) {
        logger.info("request=[REST]/clients, action=saveNewClientBegin")
        clientsRepository.save(client.toEntity()).also {
            logger.info("action=saveNewClientEnd")
        }
    }
}