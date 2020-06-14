package cz.goodsamaritan.be.queries.rest

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.client.RestTemplate
import java.net.InetAddress
import java.net.URI

@RestController
class TestController(
        @Qualifier("logRestTemplate")
        val logRestTemplate: RestTemplate) {
    val logger: Logger = LoggerFactory.getLogger(QueryController::class.java)

    @GetMapping
    fun getTest(): String {
        logger.info("request=[REST]/, action=testBegin")
        return "ahoj z informer:v1 from " + InetAddress.getLocalHost().hostName.also {
            logger.info("action=testEnd")
        }
    }

    @GetMapping("/sa")
    fun getSA(): String? {
        logger.info("request=[REST]/, action=testSa")
        return logRestTemplate.getForEntity(URI.create("https://mysadavid.blob.core.windows.net/temp/hello"), String::class.java).body.also {
            logger.info("action=testSa")
        }
    }
    @GetMapping("/sas")
    fun getSAs(): String? {
        logger.info("request=[REST]/, action=testSas")
        return logRestTemplate.getForEntity(URI.create("https://mysadavid.blob.core.windows.net/temp/hello?sp=r&st=2020-06-10T10:33:14Z&se=2020-07-10T18:33:14Z&spr=https&sv=2019-10-10&sr=b&sig=So5WvJzOGQUhFkj5mOMB79ktwW7MIPC3Xi58h2wtw44%3D"), String::class.java).body.also {
            logger.info("action=testSas")
        }
    }
}