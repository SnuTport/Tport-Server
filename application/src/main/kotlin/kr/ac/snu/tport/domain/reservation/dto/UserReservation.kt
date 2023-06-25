package kr.ac.snu.tport.domain.reservation.dto

import kr.ac.snu.tport.domain.reservation.Reservation
import kr.ac.snu.tport.domain.user.User

data class UserReservation(
    val user: User,
    val reservation: Reservation
)
