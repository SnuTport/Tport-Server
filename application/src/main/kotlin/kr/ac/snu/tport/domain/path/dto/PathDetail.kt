package kr.ac.snu.tport.domain.path.dto

import kr.ac.snu.tport.domain.bus.Bus
import kr.ac.snu.tport.domain.bus.BusStop
import kr.ac.snu.tport.domain.bus.dto.BusStopInDetail
import java.time.LocalTime

data class PathDetail(
    val id: Long,
    val getOnBusStop: String,
    val getOffBusStop: String,
    val bus: BusDetail,
    val fare: Int,
    val travelTime: Int
)

data class BusDetail(
    val busId: Long,
    val busNum: String,
    val capacity: Long,
    val departureTime: LocalTime,
    val busStop: List<BusStopInDetail>
)