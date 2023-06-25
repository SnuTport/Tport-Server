package kr.ac.snu.tport.domain.bus

import java.time.LocalTime

/**
 * 광역 버스만 관심사임
 */
data class Bus(
    val busId: Long,
    val busNum: String,
    val capacity: Long,
    val departureTime: LocalTime,
    val busStop: List<BusStop>
) : Vehicle {
    private val dict
        get() = busStop.associateBy { it.name }

    fun findArrivalTimeOf(busStop: String): LocalTime? {
        return dict[busStop]?.busArrivalTime
    }

    override fun typeName(): String {
        return Vehicle.Type.M_BUS.name
    }
}

data class BusStop(
    val name: String,
    val busArrivalTime: LocalTime,
    val forecastedDemand: Int
)
