package kr.ac.snu.tport.job

import kr.ac.snu.tport.domain.path.PathGroupService
import kr.ac.snu.tport.processor.PathGroupExcelUploader
import org.springframework.stereotype.Component

@Component
class ExcelDatabaseUploadBatchJob(
    private val pathGroupService: PathGroupService,
    private val pathGroupExcelUploader: PathGroupExcelUploader
) : BatchJob {

    override fun execute(args: Array<String>) {
        val pathGroups = pathGroupExcelUploader.execute()
        pathGroupService.saveAll(pathGroups)
    }
}
