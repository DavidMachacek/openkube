package cz.goodsamaritan.be.queries.rest

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import java.net.InetAddress

@RestController
class TestController {
    val logger: Logger = LoggerFactory.getLogger(QueryController::class.java)

    @GetMapping
    fun getAll(): String {
        logger.info("request=[REST]/, action=testBegin")
        return "ahoj z informer:v1 from " + InetAddress.getLocalHost().hostName.also {
            logger.info("action=testEnd")
        }
    }
}