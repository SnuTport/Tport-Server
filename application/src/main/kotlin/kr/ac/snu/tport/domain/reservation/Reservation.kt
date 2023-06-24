package kr.ac.snu.tport.domain.reservation

import java.time.LocalDateTime

data class Reservation(
    val busId: Long,
    val busNum: String,
    val busStopName: String,
    val seatNum: Int,
    val time: LocalDateTime
)
