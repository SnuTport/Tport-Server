package kr.ac.snu.tport.domain.path.model

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table

@Table("path_groups")
data class PathGroupEntity(
    @Id
    @Column("id")
    val id: Long? = null,
    @Column("get_on_bus_stop")
    val getOnBusStop: String,
    @Column("get_off_bus_stop")
    val getOffBusStop: String,
    @Column("fare")
    val fare: Int,
    @Column("travel_time_minutes")
    val travelTime: Int
)
