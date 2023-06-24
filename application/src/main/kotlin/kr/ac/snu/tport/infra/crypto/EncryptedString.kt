package kr.ac.snu.tport.infra.crypto

import kr.ac.snu.tport.configuration.ReadingConverterBean
import kr.ac.snu.tport.configuration.WritingConverterBean
import org.springframework.core.convert.converter.Converter

data class EncryptedString(val value: String) {

    private val encryptedValue = EncryptUtils.encrypt(value)

    @WritingConverterBean
    class WritingConverter : Converter<EncryptedString, String> {
        override fun convert(source: EncryptedString): String {
            return source.encryptedValue
        }
    }

    @ReadingConverterBean
    class ReadingConverter : Converter<String, EncryptedString> {
        override fun convert(source: String): EncryptedString {
            return EncryptedString(EncryptUtils.decrypt(source))
        }
    }
}
