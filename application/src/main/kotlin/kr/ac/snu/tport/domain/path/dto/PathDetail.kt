package kr.ac.snu.tport.domain.path.dto

import kr.ac.snu.tport.domain.bus.Bus
import kr.ac.snu.tport.domain.bus.dto.BusStopInDetail

data class PathDetail(
    val id: Long,
    val getOnBusStop: BusStopInDetail,
    val getOffBusStop: BusStopInDetail,
    val bus: Bus,
    val fare: Int,
    val travelTime: Int
)
