package cz.goodsamaritan.be.queries.rest

import cz.goodsamaritan.be.queries.model.ClientDTO
import cz.goodsamaritan.be.queries.model.ClientRepository
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.*

@CrossOrigin(origins = ["*", "http://frontends-informer.apps.ocsp1.sg-lab.local"], allowedHeaders = ["*"])
@RequestMapping("/db")
@RestController
class DatabaseController(
        val repo: ClientRepository
) {

    val logger: Logger = LoggerFactory.getLogger(DatabaseController::class.java)

    @GetMapping
    fun getClients(): List<ClientDTO> {
        logger.info("request=[REST]/db, action=getClients")
        return repo.findAll().toList().also {
            logger.info("action=getClientsEnd")
        }
    }

    @PostMapping
    fun postClient(@RequestBody client: ClientDTO): ClientDTO {
        logger.info("request=[REST]/db, action=postClient")
        return repo.save(client).also {
            logger.info("action=postClientEnd")
        }
    }

    @GetMapping("/{id}")
    fun getClient(@PathVariable (value="id") id: String): ClientDTO {
        logger.info("request=[REST]/db, action=getClient")
        return repo.findById(id.toLong()).orElse(ClientDTO(firstName = "NOTFOUND", lastName = "NOTFOUND")).also {
            logger.info("action=getClientEnd")
        }
    }

    @DeleteMapping("/{id}")
    fun deleteClient(@PathVariable (value="id") id: String) {
        logger.info("request=[REST]/db, action=deleteClient")
        return repo.deleteById(id.toLong()).also {
            logger.info("action=deleteClientEnd")
        }
    }
}