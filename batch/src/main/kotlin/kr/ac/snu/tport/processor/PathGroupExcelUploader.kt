package kr.ac.snu.tport.processor

import kr.ac.snu.tport.domain.path.PathGroup
import org.apache.poi.ss.usermodel.Row
import org.apache.poi.ss.usermodel.Sheet
import org.apache.poi.ss.usermodel.WorkbookFactory
import org.springframework.core.io.ClassPathResource
import org.springframework.stereotype.Component

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
        while (rowIterator.hasNext()) {
            val row = rowIterator.next()
            // Assuming data columns are in order: column1, column2, column3
            val column1 = row.getCell(0).stringCellValue
            val column2 = row.getCell(1).numericCellValue.toInt()
            val column3 = row.getCell(2).booleanCellValue
        }

        TODO()
    }

}