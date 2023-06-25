package kr.ac.snu.tport.configuration

import com.fasterxml.jackson.databind.ObjectMapper
import kr.ac.snu.tport.domain.user.User
import kr.ac.snu.tport.infra.crypto.EncryptUtils
import org.springframework.context.annotation.Configuration
import org.springframework.core.MethodParameter
import org.springframework.web.reactive.BindingContext
import org.springframework.web.reactive.config.WebFluxConfigurer
import org.springframework.web.reactive.result.method.HandlerMethodArgumentResolver
import org.springframework.web.reactive.result.method.annotation.ArgumentResolverConfigurer
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono

@Configuration
class WebfluxConfiguration(
    private val objectMapper: ObjectMapper
) : WebFluxConfigurer {

    override fun configureArgumentResolvers(configurer: ArgumentResolverConfigurer) {
        configurer.addCustomResolver(RequestUserArgumentResolver(objectMapper))
    }

    private class RequestUserArgumentResolver(
        private val objectMapper: ObjectMapper
    ) : HandlerMethodArgumentResolver {
        override fun supportsParameter(parameter: MethodParameter): Boolean =
            User::class.java.isAssignableFrom(parameter.parameterType)

        /**
         * NOTE : Token Never Expires
         */
        override fun resolveArgument(
            parameter: MethodParameter,
            bindingContext: BindingContext,
            exchange: ServerWebExchange
        ): Mono<Any> {
            val header = exchange.request.headers.getFirst(HEADER_KEY)!!
            val token = header.removePrefix("Bearer ")
            val decryptedString = EncryptUtils.decrypt(token)
            return objectMapper.readValue(decryptedString, User::class.java).let { Mono.just(it) }
        }
    }

    companion object {
        const val HEADER_KEY = "Authorization"
    }
}
