package cz.goodsamaritan.be.clients

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.web.filter.CommonsRequestLoggingFilter




@SpringBootApplication
class ClientsApplication {
	@Bean
	fun commonsRequestLoggingFilter(): CommonsRequestLoggingFilter? {
		val filter = CommonsRequestLoggingFilter()
		filter.setMaxPayloadLength(10000)
		filter.setIncludePayload(true)
		filter.setIncludeQueryString(true)
		filter.setIncludeHeaders(true)
		filter.setAfterMessagePrefix("type=requestInterceptor, data=")
		return filter
	}
}

fun main(args: Array<String>) {
	runApplication<ClientsApplication>(*args)
}

