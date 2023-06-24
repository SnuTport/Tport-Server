package kr.ac.snu.tport.controller

import kr.ac.snu.tport.domain.path.Path
import kr.ac.snu.tport.domain.path.PathService
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.time.LocalTime

@RestController
@RequestMapping("/api/v1")
class PathController(
    private val pathService: PathService,
) {
    data class SearchRequest(
        val originName: String,
        val destinationName: String,
        @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
        val departureTime: LocalTime,
    )

    @GetMapping("/path/search")
    suspend fun reserveBus(req: SearchRequest): List<Path> {
        return pathService.search(req.originName, req.destinationName, req.departureTime)
    }
}
