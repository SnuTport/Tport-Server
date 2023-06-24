package kr.ac.snu.tport.domain.reservation.model

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDateTime

@Table("reservations")
data class ReservationEntity(
    @Id
    @Column("id")
    val id: Long? = null,
    @Column("bus_id")
    val busId: Long,
    @Column("bus_stop_name")
    val busStopName: String,
    @Column("user_id")
    val userId: Long,
    @Column("seat_no")
    val seatNum: Int,
    @Column("reservation_time")
    val reservationTime: LocalDateTime
) {
    @Column("reg_ts")
    val regTs: LocalDateTime = LocalDateTime.now()

    @Column("upd_ts")
    val updTs: LocalDateTime = LocalDateTime.now()
}
