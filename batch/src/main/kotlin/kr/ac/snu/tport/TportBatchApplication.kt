package kr.ac.snu.tport

import kr.ac.snu.tport.job.BatchJob
import org.springframework.boot.ExitCodeGenerator
import org.springframework.boot.SpringApplication
import org.springframework.boot.WebApplicationType
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.util.StopWatch
import kotlin.system.exitProcess

@SpringBootApplication
class TportBatchApplication

fun main(args: Array<String>) {
    if (args.isEmpty() || args.size < 2) {
        throw IllegalStateException("profile and job name needed")
    }

    val profile = args[0]
    val jobNameCharArray = args[1].toCharArray()
    jobNameCharArray[0] = Character.toLowerCase(jobNameCharArray[0])
    val jobName = String(jobNameCharArray)

    System.setProperty("spring.profiles.active", profile)

    val app = SpringApplication(TportBatchApplication::class.java)
    app.webApplicationType = WebApplicationType.NONE
    val ctx = app.run(*args)

    val job = ctx.getBean(jobName)
    val stopWatch = StopWatch()
    try {
        if (job is BatchJob) {
            stopWatch.start()
            println("Start batch job [jobName=$jobName profile=$profile]")
            job.execute(args)
        }
    } catch (e: Exception) {
        println("exception thrown $e")
    } finally {
        stopWatch.stop()
        println("End batch job  ${stopWatch.totalTimeMillis} ms")

        val returnCode = SpringApplication.exit(ctx, ExitCodeGenerator { 0 })

        println("Shutdown batch job [name:$jobName, profile:$profile, returnCode:$returnCode]")
        exitProcess(returnCode)
    }
}
