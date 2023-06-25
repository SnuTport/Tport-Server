package kr.ac.snu.tport.job

interface BatchJob {
    fun execute(args: Array<String>)
}
