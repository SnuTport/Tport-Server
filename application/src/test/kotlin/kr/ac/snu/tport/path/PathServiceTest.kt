package kr.ac.snu.tport.path

import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import kr.ac.snu.tport.domain.bus.Bus
import kr.ac.snu.tport.domain.bus.BusService
import kr.ac.snu.tport.domain.bus.BusStop
import kr.ac.snu.tport.domain.path.PathService
import kr.ac.snu.tport.domain.path.model.PathEntity
import kr.ac.snu.tport.domain.path.model.PathRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import java.time.LocalDate
import java.time.LocalTime

class PathServiceTest {
    private val busMockk = mockk<BusService>()
    private val pathRepoMockk = mockk<PathRepository>()
    private val sut = PathService(pathRepoMockk, busMockk, mockk(relaxed = true))

    /**
     * 로직 검증 말고 돌아가는지만 확인함
     */
    @Test
    @DisplayName("경로검색 테스트")
    fun search(): Unit = runBlocking {
        // given
        coEvery { busMockk.findAll(any()) } returns listOf(
            Bus(
                busId = 1L,
                busNum = "관악02",
                departureTime = LocalTime.of(5, 0),
                capacity = 10L,
                busStop = listOf(
                    BusStop(
                        name = "정문",
                        busArrivalTime = LocalTime.of(13, 0),
                        forecastedDemand = 0
                    ),
                    BusStop(
                        name = "제2공학관",
                        busArrivalTime = LocalTime.of(13, 10),
                        forecastedDemand = 0
                    )
                )
            )
        )

        coEvery { pathRepoMockk.findAllByGetOnBusStopAndGetOffBusStop(any(), any()) } returns (1..20).map {
            PathEntity(
                id = 1L,
                busId = 1L,
                getOnBusStop = "정문",
                getOffBusStop = "제2공학관",
                fare = 1000,
                travelTime = it
            )
        }

        // when
        val result = sut.search(
            "정문",
            "제2공학관",
            LocalDate.now().atTime(12, 0)
        )

        // then
        assertThat(result).isNotEmpty
    }
}
