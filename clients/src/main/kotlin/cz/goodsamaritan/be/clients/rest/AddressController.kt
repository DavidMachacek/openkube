package cz.goodsamaritan.be.clients.rest

import cz.goodsamaritan.be.clients.model.AddressDTO
import cz.goodsamaritan.be.clients.model.ClientDTO
import cz.goodsamaritan.be.clients.persistence.AddressRepository
import cz.goodsamaritan.be.clients.persistence.ClientsRepository
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/address")
class AddressController(
        private val addressRepository: AddressRepository)
{
    val logger: Logger = LoggerFactory.getLogger(AddressController::class.java)

    @GetMapping("/{id}")
    fun getAddress(@PathVariable id: Long): AddressDTO {
        logger.info("request=[REST]/{id}/address, action=getAddressBegin, params=[id=$id]")
        return addressRepository.findById(id).get().toDto().also {
            logger.info("action=getAddressEnd")
        }
    }
}