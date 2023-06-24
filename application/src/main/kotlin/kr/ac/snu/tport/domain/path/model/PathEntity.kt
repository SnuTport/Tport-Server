package kr.ac.snu.tport.domain.path.model

import kr.ac.snu.tport.domain.bus.Bus
import kr.ac.snu.tport.domain.path.Path
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table

@Table("paths")
data class PathEntity(
    @Id
    @Column("id")
    val id: Long? = null,
    @Column("bus_id")
    val busId: Long,
    @Column("get_on_bus_stop")
    val getOnBusStop: String,
    @Column("get_off_bus_stop")
    val getOffBusStop: String,
    @Column("fare")
    val fare: Int,
    @Column("travel_time_minutes")
    val travelTime: Int
) {
    fun toDomain(bus: Bus): Path {
        return Path(
            id = id!!,
            getOnBusStop = getOnBusStop,
            getOffBusStop = getOffBusStop,
            bus = bus,
            fare = fare,
            travelTime = travelTime
        )
    }
}
