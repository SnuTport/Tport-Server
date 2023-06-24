package kr.ac.snu.tport.configuration

import kr.ac.snu.tport.domain.user.User
import org.springframework.context.annotation.Configuration
import org.springframework.core.MethodParameter
import org.springframework.web.reactive.BindingContext
import org.springframework.web.reactive.config.WebFluxConfigurer
import org.springframework.web.reactive.result.method.HandlerMethodArgumentResolver
import org.springframework.web.reactive.result.method.annotation.ArgumentResolverConfigurer
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono

@Configuration
class WebfluxConfiguration : WebFluxConfigurer {

    override fun configureArgumentResolvers(configurer: ArgumentResolverConfigurer) {
        configurer.addCustomResolver(RequestUserArgumentResolver())
    }

    private class RequestUserArgumentResolver : HandlerMethodArgumentResolver {
        override fun supportsParameter(parameter: MethodParameter): Boolean =
            User::class.java.isAssignableFrom(parameter.parameterType)

        override fun resolveArgument(
            parameter: MethodParameter,
            bindingContext: BindingContext,
            exchange: ServerWebExchange
        ): Mono<Any> {
            return User(1, "test", "test").let { Mono.just(it) } // TODO
        }
    }
}
