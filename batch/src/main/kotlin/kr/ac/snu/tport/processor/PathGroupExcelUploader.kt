package kr.ac.snu.tport.processor

import kr.ac.snu.tport.domain.bus.*
import kr.ac.snu.tport.domain.path.PathGroup
import org.apache.poi.ss.usermodel.Row
import org.apache.poi.ss.usermodel.Sheet
import org.apache.poi.ss.usermodel.WorkbookFactory
import org.springframework.core.io.ClassPathResource
import org.springframework.stereotype.Component
import java.text.SimpleDateFormat
import java.time.LocalTime
import java.util.*

@Component
class PathGroupExcelUploader {

    fun execute(): List<PathGroup> {
        val resource = ClassPathResource("tport_path_data_original.xlsx")
        val inputStream = resource.inputStream

        val workbook = WorkbookFactory.create(inputStream)
        val sheet: Sheet = workbook.getSheetAt(0) // 엑셀 파일의 첫번째 시트 가져오기

        val rowIterator: Iterator<Row> = sheet.iterator()
        rowIterator.next() // Skip header row
        return buildPathGroups(rowIterator).also { workbook.close() }
    }

    // TODO : make this function to build PathGroup List
    private fun buildPathGroups(rowIterator: Iterator<Row>): List<PathGroup> {

        // TODO 이런 식으로 row 돌면서 각 셀 칼럼을 가져올 수 있음
        //   적절한 데이터 클래스 선언해서 리스트로 만들어서 반환하면,
        //   PathGroupService.saveAll() 에서 저장할 것임
        val pathGroups = mutableListOf<PathGroup>()

        while (rowIterator.hasNext()) {
            val row = rowIterator.next()
            // Assuming data columns are in order: column1, column2, column3
            val getOnBusStop = row.getCell(1).stringCellValue
            val getOffBusSTop = row.getCell(2).stringCellValue
            val startTime = convertTimeStringToLocalTime(row.getCell(3).stringCellValue)
            val fare = row.getCell(4).stringCellValue.replace("원", "").toInt()
            val travelTime = convertTimeStringToInt(row.getCell(6).stringCellValue)
            val subPaths = mutableListOf<PathGroup.SubPaths>()
            for (i in 3..8){
                val typeString = row.getCell(4*i-3).stringCellValue
                val vehicleType: Vehicle.Type = when(typeString) {
                    "도보" -> Vehicle.Type.WALK
                    "버스" -> Vehicle.Type.NORMAL_BUS
                    else -> Vehicle.Type.M_BUS
                }
                val vehicleSub = when (vehicleType) {
                    Vehicle.Type.WALK -> WALK
                    Vehicle.Type.NORMAL_BUS -> NORMAL_BUS
                    Vehicle.Type.M_BUS -> Bus(
                            // 커밋을 위한 임시 입력
                            busId = 2,
                            busNum = "M4448",
                            capacity = 45,
                            departureTime = convertTimeStringToLocalTime("5시 0분"),
                            busStop = listOf(BusStop("", LocalTime.of(5, 0), 6)),
                    )
                    else -> throw IllegalArgumentException("Unknown vehicle type: $vehicleType")
                }
                val getOnBusStopSub = row.getCell(4*i-2).stringCellValue
                val getOffBusStopSub = row.getCell(4*i-1).stringCellValue
                val travelTimeSub = row.getCell(4*i).numericCellValue.toInt()
                subPaths.add(PathGroup.SubPaths(getOnBusStopSub, getOffBusStopSub, travelTimeSub, vehicleSub))
            }
            pathGroups.add(
                    PathGroup(
                    getOnBusStop = getOnBusStop,
                    getOffBusStop = getOffBusSTop,
                    startTime = startTime,
                    fare = fare,
                    travelTime = travelTime,
                    subPaths = subPaths,
                )
            )
        }

        TODO()
    }

    fun convertTimeStringToLocalTime(inputTime: String): LocalTime {
        val hour = inputTime.substringBefore("시").trim().toInt()
        val minute = inputTime.substringBefore("분").substringAfter("시").trim().toInt()
        return LocalTime.of(hour, minute)
    }

    fun convertTimeStringToInt(inputTime: String): Int {
        val hour = inputTime.substringBefore("시간").trim().toInt()
        val minute = inputTime.substringBefore("분").substringAfter("시간").trim().toInt()
        return hour * 60 + minute
    }
}
