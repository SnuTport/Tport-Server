package kr.ac.snu.tport.infra.crypto

import org.springframework.core.convert.converter.Converter

data class EncryptedString(val value: String) {

    private val encryptedValue = EncryptUtils.encrypt(value)

    @kr.ac.snu.tport.configuration.WritingConverterBean
    class WritingConverter : Converter<EncryptedString, String> {
        override fun convert(source: EncryptedString): String {
            return source.encryptedValue
        }
    }

    @kr.ac.snu.tport.configuration.ReadingConverterBean
    class ReadingConverter : Converter<String, EncryptedString> {
        override fun convert(source: String): EncryptedString {
            return EncryptedString(EncryptUtils.decrypt(source))
        }
    }
}
