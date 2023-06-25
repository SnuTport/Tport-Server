package kr.ac.snu.tport.domain.path.dto

import kr.ac.snu.tport.domain.bus.Bus
import kr.ac.snu.tport.domain.bus.BusStop
import kr.ac.snu.tport.domain.bus.dto.BusStopInDetail
import kr.ac.snu.tport.domain.path.Path
import kr.ac.snu.tport.domain.reservation.dto.UserReservation
import kotlin.random.Random

object PathDetailBuilder {

    fun build(
        path: Path,
        reservations: List<UserReservation>
    ): PathDetail {
        return PathDetail(
            id = path.id,
            getOnBusStop = path.getOnBusStop,
            getOffBusStop = path.getOffBusStop,
            fare = path.fare,
            travelTime = path.travelTime,
            bus = buildBusDetail(path.bus, reservations)
        )
    }

    private fun buildBusDetail(bus: Bus, reservations: List<UserReservation>): BusDetail {
        val reservationMap = reservations
            .groupBy { it.reservation.busStopName }

        val busCapacity = bus.capacity
        val totalReservationCount = reservations.size
        val remainingCapacity = busCapacity - totalReservationCount
        val busStopsInDetail = randomlyDistribute(remainingCapacity, bus.busStop).map { (busStop, simulatedRealCount) ->
            buildBusStopInDetail(busStop, busCapacity, simulatedRealCount, reservationMap[busStop.name] ?: emptyList())
        }

        return BusDetail(
            busId = bus.busId,
            busNum = bus.busNum,
            capacity = bus.capacity,
            departureTime = bus.departureTime,
            busStop = busStopsInDetail
        )
    }

    /**
     * randomly distribute the remaining capacity to the bus stops
     * NOTE : 항상 버스 정원이 꽉 차지 않을 수 있다 (예약 안 하고 탑승하는 사람이 아예 없을 수도 있음)
     */
    private fun randomlyDistribute(
        remainingCapacity: Long,
        busStops: List<BusStop>
    ): Map<BusStop, Int> {
        var remainingCapacitySum = remainingCapacity
        val countMap = busStops.associateWith { 0 }.toMutableMap()
        countMap.keys.shuffled().forEach { randomKey ->
            val randomValue = Random.nextInt(remainingCapacitySum.toInt())
            countMap[randomKey] = countMap[randomKey]!! + randomValue
            remainingCapacitySum -= randomValue
        }

        return countMap
    }

    private fun buildBusStopInDetail(
        busStop: BusStop,
        busCapacity: Long,
        simulatedRealCount: Int, // 실제 비예약 탑승자 수를 시뮬레이션 한 값
        reservations: List<UserReservation>
    ): BusStopInDetail {
        val reservationCount = reservations.size // 예약자 수
        val forecastingDemand = busStop.forecastedDemand // 예측 수요
        val forecastedEmptyCount = busCapacity - forecastingDemand // 예측 빈자리 수
        val realEmptyCount = forecastedEmptyCount - simulatedRealCount // 실제 빈자리 수를 시뮬레이션 한 값

        val forecastingBusStopData = BusStopInDetail.ForecastingBusStopData(
            demand = forecastingDemand, // 에측 수요
            emptyNum = forecastedEmptyCount.toInt(), // 예측 빈자리 수
            unreservedNum = (forecastingDemand - reservationCount), // 예측 비예약 탑승자 수
            reservedNum = reservationCount, // 예약자 수
            reservationList = reservations.map { it.user }
        )

        val actualBusStopData = BusStopInDetail.ActualBusStopData(
            demand = forecastingDemand + simulatedRealCount, // 실제 수요
            emptyNum = realEmptyCount.toInt(), // 실제 빈자리 수를 시뮬레이션 한 값
            unreservedNum = simulatedRealCount, // 실제 비예약 탑승자 수를 시뮬레이션 한 값
            reservedNum = reservationCount, // 예약자 수
            reservationList = reservations.map { it.user }
        )

        return BusStopInDetail(
            name = busStop.name,
            busArrivalTime = busStop.busArrivalTime,
            forecastingBusStopData = forecastingBusStopData,
            actualBusStopData = actualBusStopData
        )
    }

}
