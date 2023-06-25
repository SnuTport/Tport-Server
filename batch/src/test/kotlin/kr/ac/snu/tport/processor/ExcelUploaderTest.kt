package kr.ac.snu.tport.processor

import kr.ac.snu.tport.job.ExcelDatabaseUploadBatchJob
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class ExcelUploaderTest @Autowired constructor(
    private val uploader: PathGroupExcelUploader
) {

    @Test
    fun `test`() {
        uploader.execute()
    }

}