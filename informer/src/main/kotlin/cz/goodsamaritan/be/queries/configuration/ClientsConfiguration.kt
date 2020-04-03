package cz.goodsamaritan.be.queries.configuration

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.client.RestTemplate

@Configuration
@EnableConfigurationProperties(ClientsProperties::class)
class ClientsConfiguration(
private val properties: ClientsProperties
) {

    @Bean
    fun clientsRestTemplate(builder: RestTemplateBuilder, properties: ClientsProperties): RestTemplate {
        println("URL JE ${properties.url}")
        return builder.rootUri(properties.url).build()
    }
}

@ConfigurationProperties(prefix = "clients")
class ClientsProperties {
    lateinit var url: String
}