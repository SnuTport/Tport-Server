package kr.ac.snu.tport.domain.bus

import java.time.LocalTime

data class Bus(
    val busId: Long,
    val busNum: String,
    val capacity: Long,
    val departureTime: LocalTime,
    val busStop: List<BusStop>
) {
    private val dict
        get() = busStop.associateBy { it.name }

    fun findArrivalTimeOf(busStop: String): LocalTime? {
        return dict[busStop]?.busArrivalTime
    }
}

data class BusStop(
    val name: String,
    val busArrivalTime: LocalTime,
    val forecastedDemand: Int
)
