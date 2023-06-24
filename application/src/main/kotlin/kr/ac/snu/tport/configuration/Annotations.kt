package kr.ac.snu.tport.configuration

import org.springframework.data.convert.ReadingConverter
import org.springframework.data.convert.WritingConverter
import org.springframework.stereotype.Component

@WritingConverter
@Component
annotation class WritingConverterBean

@ReadingConverter
@Component
annotation class ReadingConverterBean
