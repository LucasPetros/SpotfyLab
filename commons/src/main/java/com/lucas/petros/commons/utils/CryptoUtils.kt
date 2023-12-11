package com.lucas.petros.commons.utils

import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import java.security.KeyStore
import java.util.Base64
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.GCMParameterSpec

class CryptoUtils : CryptoManager {

    private val keyStore: KeyStore by lazy {
        KeyStore.getInstance(ANDROID_KEYSTORE).apply {
            load(null)
        }
    }

    private val secretKey: SecretKey by lazy {
        generateKey()
    }

    private fun generateKey(): SecretKey {
        return if (keyStore.containsAlias(KEY_ALIAS)) {
            keyStore.getKey(KEY_ALIAS, null) as SecretKey
        } else {
            val keyGenerator =
                KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, ANDROID_KEYSTORE)
            keyGenerator.init(
                KeyGenParameterSpec.Builder(
                    KEY_ALIAS,
                    KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
                )
                    .setBlockModes(KeyProperties.BLOCK_MODE_GCM)
                    .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
                    .build()
            )
            keyGenerator.generateKey()
        }
    }

    override fun encrypt(value: String): String {
        val cipher = Cipher.getInstance(TRANSFORMATION)
        cipher.init(Cipher.ENCRYPT_MODE, secretKey)

        val ivBytes = cipher.iv
        val encryptedValue = cipher.doFinal(value.toByteArray())

        val encodedIV = Base64.getEncoder().encodeToString(ivBytes)
        val encodedValue = Base64.getEncoder().encodeToString(encryptedValue)

        return "$encodedIV:$encodedValue"
    }

    override fun decrypt(encryptedData: String): String {
        val (encodedIV, encodedValue) = encryptedData.split(":")
        val ivBytes = Base64.getDecoder().decode(encodedIV)
        val encryptedValue = Base64.getDecoder().decode(encodedValue)

        val cipher = Cipher.getInstance(TRANSFORMATION)
        val spec = GCMParameterSpec(128, ivBytes)
        cipher.init(Cipher.DECRYPT_MODE, secretKey, spec)

        val decryptedValue = cipher.doFinal(encryptedValue)

        return String(decryptedValue)
    }

    override fun generateIV(): String {
        val cipher = Cipher.getInstance(TRANSFORMATION)
        cipher.init(Cipher.ENCRYPT_MODE, secretKey)
        val ivBytes = cipher.iv
        return Base64.getEncoder().encodeToString(ivBytes)
    }

    companion object {
        const val TRANSFORMATION = "AES/GCM/NoPadding"
        const val ANDROID_KEYSTORE = "AndroidKeyStore"
        const val KEY_ALIAS = "AuthTokenKey"
    }
}

interface CryptoManager {
    fun encrypt(value: String): String
    fun decrypt(encryptedData: String): String
    fun generateIV(): String
}