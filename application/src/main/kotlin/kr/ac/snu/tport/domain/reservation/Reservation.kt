package kr.ac.snu.tport.domain.reservation

import kr.ac.snu.tport.domain.bus.Bus
import kr.ac.snu.tport.domain.bus.BusStop
import java.time.LocalDateTime

data class Reservation(
    val busId: Long,
    val busNum: String,
    val busStopName: String,
    val seatNum: Int,
    val time: LocalDateTime
)
