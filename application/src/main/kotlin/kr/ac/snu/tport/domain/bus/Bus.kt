package kr.ac.snu.tport.domain.bus

data class Bus(
    val busId: Long,
    val busNum: String,
    val departureTime: Int,
    val busStop: List<BusStop>
)
