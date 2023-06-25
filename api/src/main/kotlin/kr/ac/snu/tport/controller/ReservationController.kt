package kr.ac.snu.tport.controller

import io.swagger.v3.oas.annotations.Operation
import kr.ac.snu.tport.domain.reservation.Reservation
import kr.ac.snu.tport.domain.reservation.ReservationService
import kr.ac.snu.tport.domain.user.User
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDateTime

@RestController
@RequestMapping("/api/v1")
class ReservationController(
    private val reservationService: ReservationService
) {

    data class ReservationRequest(
        val busId: Long,
        val getOnBusStop: String,
        val reservationRequestDatetime: LocalDateTime
    )

    @PostMapping("/reservation")
    @Operation(
        method = "버스 예약 API",
        parameters = [
            io.swagger.v3.oas.annotations.Parameter(
                name = "busId",
                description = "버스 ID",
                required = true,
                example = "1",
            ),
            io.swagger.v3.oas.annotations.Parameter(
                name = "getOnBusStop",
                description = "탑승 정류장 이름",
                required = true,
                example = "서울대입구역",
            ),
            io.swagger.v3.oas.annotations.Parameter(
                name = "reservationRequestDatetime",
                description = "예약 요청 시간",
                required = true,
                example = "2021-06-01T12:00:00",
            ),
        ]
    )
    suspend fun reserveBus(
        user: User,
        @RequestBody req: ReservationRequest
    ): Reservation {
        return reservationService.postReservation(user, req.busId, req.getOnBusStop, req.reservationRequestDatetime)
    }
}
