package kr.ac.snu.tport.domain.path

import kr.ac.snu.tport.domain.bus.Vehicle
import java.time.LocalTime

data class PathGroup(
    val id: Long? = null,
    val getOnBusStop: String,
    val getOffBusStop: String,
    val startTime: LocalTime,
    val fare: Int,
    val travelTime: Int,
    val subPaths: List<SubPaths>
) {
    data class SubPaths(
        val getOnBusStop: String,
        val getOffBusStop: String,
        val travelTime: Int,
        val vehicle: Vehicle
    )
}
