package cz.goodsamaritan.be.offers

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.client.BufferingClientHttpRequestFactory
import org.springframework.http.client.SimpleClientHttpRequestFactory
import org.springframework.http.converter.ByteArrayHttpMessageConverter
import org.springframework.http.converter.StringHttpMessageConverter
import org.springframework.validation.annotation.Validated
import org.springframework.web.client.RestTemplate
import javax.validation.constraints.NotBlank

@ConfigurationProperties(prefix = "informer")
@Validated
class InformerProperties(
        @get:NotBlank
        var url: String = ""
)

@Configuration
class RestTemplateInformerConfiguration {

        @Bean
        fun restTemplateInformer(props: InformerProperties, loggingInterceptor: LoggingInterceptor, restTemplateBuilder: RestTemplateBuilder): RestTemplate {
                val factory = BufferingClientHttpRequestFactory(SimpleClientHttpRequestFactory())
                val restTemplate = restTemplateBuilder.requestFactory{ factory }.rootUri(props.url).build()
                restTemplate.interceptors = listOf(loggingInterceptor)
                restTemplate.messageConverters = listOf(ByteArrayHttpMessageConverter(), StringHttpMessageConverter())
                return restTemplate
        }
}
