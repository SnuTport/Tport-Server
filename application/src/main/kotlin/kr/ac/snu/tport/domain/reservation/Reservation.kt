package kr.ac.snu.tport.domain.reservation

import kr.ac.snu.tport.domain.bus.Bus
import kr.ac.snu.tport.domain.bus.BusStop

data class Reservation(
    val bus: Bus,
    val busStop: BusStop,
    val seatNum: Int,
    val time: Int
)