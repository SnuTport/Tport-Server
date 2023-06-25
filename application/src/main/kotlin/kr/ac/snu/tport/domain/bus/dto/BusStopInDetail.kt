package kr.ac.snu.tport.domain.bus.dto

import kr.ac.snu.tport.domain.user.User
import java.time.LocalTime

data class BusStopInDetail(
    val name: String,
    val busArrivalTime: LocalTime,
    val forecastingBusStopData: ForecastingBusStopData,
    val actualBusStopData: ActualBusStopData
) {
    data class ForecastingBusStopData(
        val demand: Int,
        val emptyNum: Int,
        val unreservedNum: Int,
        val reservedNum: Int,
        val reservationList: List<User>
    )

    data class ActualBusStopData(
        val demand: Int,
        val emptyNum: Int,
        val unreservedNum: Int,
        val reservedNum: Int,
        val reservationList: List<User>
    )
}
