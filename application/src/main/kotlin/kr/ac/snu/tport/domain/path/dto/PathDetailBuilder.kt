package kr.ac.snu.tport.domain.path.dto

import kr.ac.snu.tport.domain.bus.Bus
import kr.ac.snu.tport.domain.bus.BusStop
import kr.ac.snu.tport.domain.bus.dto.BusStopInDetail
import kr.ac.snu.tport.domain.path.Path
import kr.ac.snu.tport.domain.reservation.dto.UserReservation

object PathDetailBuilder {

    fun build(
        path: Path,
        reservations: List<UserReservation>
    ): PathDetail {
        val busStopDetails =
            buildBusStopDetail(path.bus, path.getOnBusStop, path.getOffBusStop, reservations)

        return PathDetail(
            id = path.id,
            getOnBusStop = busStopDetails.onBusStop,
            getOffBusStop = busStopDetails.offBusStop,
            bus = path.bus,
            fare = path.fare,
            travelTime = path.travelTime
        )
    }

    private fun buildBusStopDetail(
        bus: Bus,
        onBusStopName: String,
        offBusStopName: String,
        reservations: List<UserReservation>
    ): OnOffBusStopDetail {
        val reservationMap = reservations
            .groupBy { it.reservation.busStopName }

        val onBusStop = bus.busStop.find { it.name == onBusStopName }!!
        val offBusStop = bus.busStop.find { it.name == offBusStopName }!!
        val busCapacity = bus.capacity

        return OnOffBusStopDetail(
            onBusStop = buildBusStopDetail(onBusStop, busCapacity, reservationMap[onBusStop.name]!!),
            offBusStop = buildBusStopDetail(offBusStop, busCapacity, reservationMap[offBusStop.name]!!)
        )
    }

    private fun buildBusStopDetail(
        busStop: BusStop,
        busCapacity: Long,
        reservations: List<UserReservation>
    ): BusStopInDetail {
        val forecastingDemand = busStop.forecastedDemand // 예측 수요
        val reservationCount = reservations.size // 예약자 수

        val forecastingBusStopData = BusStopInDetail.ForecastingBusStopData(
            demand = forecastingDemand,
            reservedNum = reservationCount, // 예약자 수
            unreservedNum = (forecastingDemand - reservationCount), // 예측 비예약 탑승자 수
            emptyNum = (busCapacity - forecastingDemand).toInt(), // 예측 빈자리 수
            reservationList = reservations.map { it.user }
        )

        // TODO
        val realCount = 0L // 실제 비예약 탑승자 수 (?)
        val realEmptyCount = busCapacity - forecastingDemand // 실제 빈자리 수
        val actualBusStopData = BusStopInDetail.ActualBusStopData(
            demand = 9999,
            reservedNum = 9999,
            unreservedNum = 9999,
            emptyNum = 9999,
            reservationList = emptyList()
        )

        return BusStopInDetail(
            name = busStop.name,
            busArrivalTime = busStop.busArrivalTime,
            forecastingBusStopData = forecastingBusStopData,
            actualBusStopData = actualBusStopData
        )
    }

    private data class OnOffBusStopDetail(
        val onBusStop: BusStopInDetail,
        val offBusStop: BusStopInDetail
    )
}
