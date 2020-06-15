package cz.goodsamaritan.be.offers

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication

@EnableConfigurationProperties(InformerProperties::class)
@SpringBootApplication
class OffersApplication

fun main(args: Array<String>) {
	runApplication<OffersApplication>(*args)
}
