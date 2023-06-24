package kr.ac.snu.tport.controller

import kr.ac.snu.tport.domain.reservation.ReservationService
import kr.ac.snu.tport.domain.user.User
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1")
class ReservationController(
    private val reservationService: ReservationService
) {

    data class ReservationRequest(
        val busId: Long,
        val getOnBusStop: String,
    )

    @PostMapping("/reservation")
    suspend fun reserveBus(
        user: User,
        @RequestBody req: ReservationRequest
    ): String {
        return reservationService.postReservation(user, req.busId, req.getOnBusStop)
    }
}
