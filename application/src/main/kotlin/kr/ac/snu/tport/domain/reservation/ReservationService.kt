package kr.ac.snu.tport.domain.reservation

import kotlinx.coroutines.flow.toList
import kr.ac.snu.tport.domain.bus.Bus
import kr.ac.snu.tport.domain.bus.model.BusRepository
import kr.ac.snu.tport.domain.bus.model.BusStopRepository
import kr.ac.snu.tport.domain.reservation.dto.UserReservation
import kr.ac.snu.tport.domain.reservation.model.ReservationEntity
import kr.ac.snu.tport.domain.reservation.model.ReservationRepository
import kr.ac.snu.tport.domain.user.User
import kr.ac.snu.tport.domain.user.UserService
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate
import java.time.LocalTime

@Component
class ReservationService(
    private val busRepository: BusRepository,
    private val busStopRepository: BusStopRepository,
    private val reservationRepository: ReservationRepository,
    private val userService: UserService
) {
    /**
     * TODO API LOCK 필요함 ~~
     */
    @Transactional
    suspend fun postReservation(
        user: User,
        busId: Long,
        busStopName: String
    ): Reservation {
        val bus = busRepository.findById(busId)!!
        val busStop = busStopRepository.findByBusStopName(busStopName)!! // TODO 에러 메시지 포맷
        val arrivalTime = LocalDate.now().atTime(LocalTime.parse(busStop.arrivalTime))

        val reservations = reservationRepository.findAllByBusIdAndBusStopName(busId, busStop.busStopName)
        if (reservations.size >= bus.capacity) {
            throw IllegalStateException("예약 정원이 꽉 찼습니다.")
        }

        val seatNum = reservations.size + 1
        val reservation = ReservationEntity(
            busId = busId,
            userId = user.id,
            busStopName = busStop.busStopName,
            seatNum = seatNum,
            reservationTime = arrivalTime
        )

        return reservationRepository
            .save(reservation)
            .let {
                Reservation(it.userId, busId, bus.busNum, busStop.busStopName, seatNum, arrivalTime)
            }
    }

    suspend fun getReservations(buses: List<Bus>): Map<Bus, List<UserReservation>> {
        val busMap = buses.associateBy { it.busId }
        val reservations = reservationRepository.findAllByBusIdIn(busMap.keys.toList())
        val users = userService.findAll(reservations.map { it.userId }.distinct())
            .toList()
            .associateBy { it.id }

        return reservations
            .map {
                UserReservation(
                    user = users[it.userId]!!,
                    reservation = Reservation(it.userId, it.busId, it.busStopName, it.busStopName, it.seatNum, it.reservationTime)
                )
            }
            .groupBy { busMap[it.reservation.busId]!! }
    }
}
