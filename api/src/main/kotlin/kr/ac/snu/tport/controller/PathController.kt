package kr.ac.snu.tport.controller

import io.swagger.v3.oas.annotations.Operation
import kr.ac.snu.tport.domain.path.PathService
import kr.ac.snu.tport.domain.path.dto.PathDetail
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
    @Operation(
        method = "경로 조회 API",
        parameters = [
            io.swagger.v3.oas.annotations.Parameter(
                name = "originName",
                description = "출발 정류장 이름",
                required = true,
                example = "서울대입구역",
            ),
            io.swagger.v3.oas.annotations.Parameter(
                name = "destinationName",
                description = "도착 정류장 이름",
                required = true,
                example = "서울대학교",
            ),
            io.swagger.v3.oas.annotations.Parameter(
                name = "departureTime",
                description = "출발 시간",
                required = true,
                example = "10:00",
            ),
        ]
    )
    suspend fun searchPaths(req: SearchRequest): List<PathDetail> {
        return pathService.search(req.originName, req.destinationName, req.departureTime)
    }
}
