package kr.ac.snu.tport.domain.path

import kr.ac.snu.tport.domain.bus.Vehicle

data class PathGroup(
    val id: Long? = null,
    val getOnBusStop: String,
    val getOffBusStop: String,
    val fare: Int,
    val travelTime: Int,
    val subPaths: List<SubPaths>
) {
    data class SubPaths(
        val getOnBusStop: String,
        val getOffBusStop: String,
        val vehicle: Vehicle
    )
}
