package kr.ac.snu.tport.domain.path

import kr.ac.snu.tport.domain.bus.Bus

data class Path(
    val id: Long,
    val getOnBusStop: String,
    val getOffBusStop: String,
    val bus: Bus,
    val fare: Int,
    val travelTime: Int
)
