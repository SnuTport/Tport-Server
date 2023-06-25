package kr.ac.snu.tport

import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.annotations.info.Info
import io.swagger.v3.oas.annotations.servers.Server
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@OpenAPIDefinition(
    info = Info(
        title = "SNU tPort Server",
        version = "v1",
        description = "API Documents (v1)"
    ),
    servers = [Server(url = "/")]
)
@SpringBootApplication
class TportServerApplication

fun main(args: Array<String>) {
    runApplication<TportServerApplication>(*args)
}
