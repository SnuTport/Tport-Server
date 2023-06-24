package kr.ac.snu.tport.domain.bus

import kr.ac.snu.tport.domain.user.User
import java.time.LocalTime

data class BusStop(
    val name: String,
    val busArrivalTime: LocalTime,
    // TODO 아래 필드들 도메인 분리
    val forecastingBusStopData: ForecastingBusStopData? = null,
    val actualBusStopData: ActualBusStopData? = null
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
