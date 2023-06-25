package kr.ac.snu.tport.domain.bus.model

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table
import java.time.LocalTime

@Table("bus_bus_stops")
data class BusStopEntity(
    @Id
    @Column("id")
    val id: Long? = null,
    @Column("bus_id")
    val busId: Long,
    @Column("bus_stop_name")
    val busStopName: String,
    @Column("arrival_time")
    val arrivalTime: String,
    @Column("forecasted_demand")
    val forecastedDemand: Int,
) {
    init {
        // this throws exception if not valid
        LocalTime.parse(arrivalTime)
    }
}
