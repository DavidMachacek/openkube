package cz.goodsamaritan.be.offers

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
        val logRestTemplate: RestTemplate,
        @Qualifier("restTemplateInformer")
        val restTemplateInformer: RestTemplate) {
    val logger: Logger = LoggerFactory.getLogger(TestController::class.java)

    @GetMapping
    fun getTest(): String {
        logger.info("request=[REST]/, action=testBegin")
        return "ahoj z offeru:v1 from " + InetAddress.getLocalHost().hostName.also {
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


    @GetMapping("/informer")
    fun getSAs(): String? {
        logger.info("request=[REST]/, action=getInformerBegin")
        return restTemplateInformer.getForEntity("/", String::class.java).body.also {
            logger.info("action=getInformerEnd")
        }
    }
}