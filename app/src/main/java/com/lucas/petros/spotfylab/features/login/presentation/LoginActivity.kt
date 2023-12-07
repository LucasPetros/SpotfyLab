package com.lucas.petros.spotfylab.features.login.presentation

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.lucas.petros.commons.data.Constants.ACCESS_TOKEN
import com.lucas.petros.commons.utils.SecureTokenManager
import com.lucas.petros.spotfylab.R
import com.lucas.petros.spotfylab.HomeActivity
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {
    @Inject
    lateinit var secureTokenManager: SecureTokenManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        if (savedInstanceState == null && secureTokenManager.getToken(ACCESS_TOKEN) == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, LoginFragment())
                .commit()
        } else {
            startActivity(Intent(this, HomeActivity::class.java))
        }
    }
}