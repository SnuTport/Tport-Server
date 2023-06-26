package kr.ac.snu.tport.domain.path.dto

import kr.ac.snu.tport.domain.bus.dto.BusStopInDetail
import kr.ac.snu.tport.domain.path.PathGroup
import java.time.LocalTime
data class PathGroupDetail(
    val id: Long,
    val getOnBusStop: String,
    val getOffBusStop: String,
    val departureTime: LocalTime,
    val fare: Int,
    val travelTime: Int,
    val subPaths: List<PathGroup.SubPaths>,
    val metroSubPath: PathGroup.SubPaths,
    val metroBusDetail: BusDetail
)

data class BusDetail(
    val busId: Long,
    val busNum: String,
    val capacity: Long,
    val departureTime: LocalTime,
    val busStop: List<BusStopInDetail>
)
