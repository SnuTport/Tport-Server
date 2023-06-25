package kr.ac.snu.tport.domain.path.model

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table

@Table("sub_paths")
data class SubPathsEntity(
    @Id
    @Column("id")
    val id: Long? = null,
    @Column("get_on_bus_stop")
    val getOnBusStop: String,
    @Column("get_off_bus_stop")
    val getOffBusStop: String,
    @Column("path_group_id")
    val pathGroupId: Long?,
    @Column("bus_id")
    val busId: Long?,
    @Column("type")
    val type: String,
)
