package com.lucas.petros.commons.utils

import android.content.SharedPreferences
import android.util.Log
import com.lucas.petros.commons.extension.handleOpt
import javax.inject.Inject

class SecureTokenManager @Inject constructor(
    private val sharedPreferences: SharedPreferences,
    private val cryptoManager: CryptoManager
) {

    fun saveToken(keyType: String, value: String) {
        try {
            val encryptedValue = cryptoManager.encrypt(value)
            val iv = cryptoManager.generateIV()

            sharedPreferences.edit()
                .putString(keyType + "IV", iv)
                .putString(keyType, encryptedValue)
                .apply()

            Log.d("SecureTokenManager", "Token salvo: $value")
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun getToken(keyType: String): String? {
        try {
            val encryptedToken =
                sharedPreferences.getString(keyType, "").handleOpt()

            if (encryptedToken.isNotBlank()) {
                return cryptoManager.decrypt(encryptedToken)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return null
    }


}