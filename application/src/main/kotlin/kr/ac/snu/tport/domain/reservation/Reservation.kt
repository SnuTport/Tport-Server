package kr.ac.snu.tport.domain.reservation

import java.time.LocalDateTime

data class Reservation(
    val userId: Long,
    val busId: Long,
    val busNum: String,
    val busStopName: String,
    val seatNum: Int,
    val time: LocalDateTime
)
