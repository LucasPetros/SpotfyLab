package com.lucas.petros.commons.utils

import android.content.SharedPreferences
import android.util.Log
import com.lucas.petros.commons.extension.handleOpt
import javax.inject.Inject

class Prefs @Inject constructor(
    private val sharedPreferences: SharedPreferences,
    private val cryptoManager: CryptoManager
) {

    companion object {
        const val PREFS_FILE_NAME = "ragnarok"
        const val KEY_ACCESS_TOKEN = "token"
        const val KEY_TOKEN_REFRESH = "refresh_token"
        const val KEY_USER_ID = "spotifyId"
    }

    fun saveEncrypted(keyType: String, value: String) {
        if (value.isNotBlank()) {
            try {
                val encryptedValue = cryptoManager.encrypt(value)
                val iv = cryptoManager.generateIV()

                sharedPreferences.edit()
                    .putString(keyType + "IV", iv)
                    .putString(keyType, encryptedValue)
                    .apply()
                Log.d("TESTE", "saveEncrypted: salvei vazio $value")

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun getDecrypted(keyType: String): String? {
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