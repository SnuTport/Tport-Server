package kr.ac.snu.tport.domain.reservation.model

import org.springframework.data.repository.kotlin.CoroutineCrudRepository

interface ReservationRepository : CoroutineCrudRepository<ReservationEntity, Long> {
    suspend fun findAllByBusIdAndBusStopName(busId: Long, busStop: String): List<ReservationEntity>
    suspend fun findAllByBusIdIn(busIds: List<Long>): List<ReservationEntity>
}
