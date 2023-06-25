package kr.ac.snu.tport.domain.bus

/**
 * 다른 교통수단들
 */
sealed interface Vehicle {
    fun typeName(): String

    enum class Type {
        WALK, NORMAL_BUS, M_BUS
    }
}

object WALK : Vehicle {
    override fun typeName(): String = Vehicle.Type.WALK.name
}

object NORMAL_BUS : Vehicle {
    override fun typeName(): String = Vehicle.Type.NORMAL_BUS.name
}
