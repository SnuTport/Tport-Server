package kr.ac.snu.tport.domain.bus

import kr.ac.snu.tport.domain.user.User

data class BusStop(
    val name: String,
    val busArrivalTime: Int,
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
