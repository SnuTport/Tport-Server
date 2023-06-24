package kr.ac.snu.tport.infra.crypto

import org.jasypt.encryption.StringEncryptor

object EncryptUtils {
    private lateinit var jasyptEncryptor: StringEncryptor

    fun setEncryptor(encryptor: StringEncryptor) {
        jasyptEncryptor = encryptor
    }

    fun encrypt(plainText: String): String {
        if (plainText.trim().isBlank()) {
            return ""
        }

        return try {
            jasyptEncryptor.encrypt(plainText) ?: ""
        } catch (e: Exception) {
            TODO("unhandled exception : $e")
        }
    }

    fun decrypt(encrypted: String): String {
        if (encrypted.trim().isBlank()) {
            return ""
        }

        return try {
            jasyptEncryptor.decrypt(encrypted) ?: ""
        } catch (e: Exception) {
            TODO("add log")
        }
    }
}
