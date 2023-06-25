package kr.ac.snu.tport.domain.path.dto

import kr.ac.snu.tport.domain.bus.Bus
import kr.ac.snu.tport.domain.bus.BusStop
import kr.ac.snu.tport.domain.bus.dto.BusStopInDetail
import kr.ac.snu.tport.domain.path.Path
import kr.ac.snu.tport.domain.reservation.dto.UserReservation
import java.time.LocalDateTime
import kotlin.math.min
import kotlin.random.Random

object PathDetailBuilder {

    fun build(
        path: Path,
        reservations: List<UserReservation>,
        departureTime: LocalDateTime
    ): PathDetail {
        return PathDetail(
            id = path.id,
            getOnBusStop = path.getOnBusStop,
            getOffBusStop = path.getOffBusStop,
            fare = path.fare,
            travelTime = path.travelTime,
            bus = buildBusDetail(path.bus, reservations, departureTime)
        )
    }

    private fun buildBusDetail(
        bus: Bus,
        reservations: List<UserReservation>,
        departureTime: LocalDateTime
    ): BusDetail {
        val reservationMap = reservations
            .groupBy { it.reservation.busStopName }

        val busCapacity = bus.capacity
        val totalReservationCount = reservations.size
        val remainingCapacity = busCapacity - totalReservationCount
        val busStopsWithSimulationValues = randomlyDistribute(
            remainingCapacity,
            bus.busStop,
            departureTime,
        )

        var actualDataRightBefore: BusStopInDetail.ActualBusStopData? = null
        val busStopsInDetail = busStopsWithSimulationValues.map { (busStop, simulatedRealCount) ->
            buildBusStopInDetail(
                busStop,
                busCapacity.toInt(),
                simulatedRealCount,
                reservationMap[busStop.name].orEmpty(),
                actualDataRightBefore
            ).also { actualDataRightBefore = it.actualBusStopData }
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
        busStops: List<BusStop>,
        departureTime: LocalDateTime
    ): Map<BusStop, Int> {
        var remainingCapacitySum = remainingCapacity
        val countMap = busStops.associateWith { 0 }.toMutableMap()
        countMap.keys.shuffled().forEach { randomKey ->
            val busStopClosestArrivalTime = departureTime.toLocalDate().atTime(randomKey.busArrivalTime)
            if (busStopClosestArrivalTime.isAfter(LocalDateTime.now())) {
                // 버스의 도착 시간이 조회 시점 이후라면,
                // 실제 탑승자가 존재할 수 없으므로, 빈자리 수를 더해주지 않는다.
                return@forEach
            }

            // 좀 더 그럴 듯한 데이터를 위해 실제 값이 예측값의 2배를 넘지 않도록 조정
            val upperLimit = min(remainingCapacitySum.toInt(), randomKey.forecastedDemand * 2)
            val randomValue = if (upperLimit <= 0L) 0 else Random.nextInt(0, upperLimit)
            countMap[randomKey] = countMap[randomKey]!! + randomValue
            remainingCapacitySum -= randomValue
        }

        return countMap
    }

    private fun buildBusStopInDetail(
        busStop: BusStop,
        busCapacity: Int,
        simulatedRealCount: Int, // 실제 비예약 탑승자 수를 시뮬레이션 한 값
        reservations: List<UserReservation>,
        actualDataRightBefore: BusStopInDetail.ActualBusStopData?
    ): BusStopInDetail {
        val realEmptyNumRightBefore = actualDataRightBefore?.emptyNum ?: busCapacity // 직전 실제 빈자리 수
        val forecastingDemand = busStop.forecastedDemand // 예측 수요
        val forecastedEmptyCount = realEmptyNumRightBefore - forecastingDemand // 이번의 예측 빈자리 수
        val realEmptyCount = realEmptyNumRightBefore - simulatedRealCount // 이번의 실제 빈자리 수를 시뮬레이션 한 값

        val reservationCount = reservations.size // 예약자 수

        val forecastingBusStopData = BusStopInDetail.ForecastingBusStopData(
            demand = forecastingDemand, // 에측 수요
            emptyNum = forecastedEmptyCount, // 예측 빈자리 수
            unreservedNum = (forecastingDemand - reservationCount), // 예측 비예약 탑승자 수
            reservedNum = reservationCount, // 예약자 수
            reservationList = reservations.map { it.user }
        )

        val actualBusStopData = BusStopInDetail.ActualBusStopData(
            demand = reservationCount + simulatedRealCount, // 실제 수요
            emptyNum = realEmptyCount, // 실제 빈자리 수를 시뮬레이션 한 값
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
