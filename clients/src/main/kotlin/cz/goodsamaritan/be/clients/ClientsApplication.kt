package cz.goodsamaritan.be.clients

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class ClientsApplication

fun main(args: Array<String>) {
	runApplication<ClientsApplication>(*args)
}
