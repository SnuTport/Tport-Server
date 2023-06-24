package kr.ac.snu.tport.domain.bus.model

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table
import java.time.LocalTime

@Table("buses")
data class BusEntity(
    @Id
    @Column("id")
    val id: Long? = null,
    @Column("bus_number")
    val busNum: String,
    @Column("capacity")
    val capacity: Long,
    @Column("departure_time")
    val departureTime: String
) {
    init {
        // this throws exception if not valid
        LocalTime.parse(departureTime)
    }
}
