package cz.goodsamaritan.be.queries.rest

import cz.goodsamaritan.be.queries.facade.ClientsFacade
import cz.goodsamaritan.be.queries.model.ClientDTO
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.*

@RequestMapping("/api/clients")
@RestController
class QueryController(
        private val clientsFacade: ClientsFacade
) {

    val logger: Logger = LoggerFactory.getLogger(QueryController::class.java)

    @GetMapping
    fun getAll(): List<ClientDTO> {
        logger.info("request=[REST]/api/clients, action=getAllClientsBegin")
        return clientsFacade.getAll().also {
            logger.info("action=getAllClientsEnd")
        }
    }

    @GetMapping("/{id}")
    fun getOne(@PathVariable id: Long): ClientDTO {
        logger.info("request=[REST]/api/clients/{id}, action=getOneClientBegin, params=[id=$id]")
        return clientsFacade.getOne(id).also {
            logger.info("action=getOneClientEnd")
        }
    }

    @PostMapping
    fun create(client: ClientDTO) {
        logger.info("request=[REST]/api/clients, action=saveNewClientBegin")
        clientsFacade.saveNew(client).also {
            logger.info("action=saveNewClientEnd")
        }
    }
}