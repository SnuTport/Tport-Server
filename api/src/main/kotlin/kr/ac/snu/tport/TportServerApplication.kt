package kr.ac.snu.tport

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class TportServerApplication

fun main(args: Array<String>) {
    runApplication<TportServerApplication>(*args)
}
