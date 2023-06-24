package kr.ac.snu.tport.domain.bus.model

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table

@Table("bus_stops")
data class BusStopEntity(
    @Id
    @Column("id")
    val id: Long? = null,
    @Column("name")
    val name: String
)
