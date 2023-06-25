package kr.ac.snu.tport.domain.reservation.model

import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import java.time.LocalDateTime

interface ReservationRepository : CoroutineCrudRepository<ReservationEntity, Long> {
    suspend fun findAllByBusIdAndBusStopName(busId: Long, busStop: String): List<ReservationEntity>
    suspend fun findAllByBusIdInAndReservationTimeBetween(
        busIds: List<Long>,
        from: LocalDateTime,
        to: LocalDateTime,
    ): List<ReservationEntity>
}
