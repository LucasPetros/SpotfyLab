package com.lucas.petros.spotfylab.di

import android.content.Context
import android.content.SharedPreferences
import com.lucas.petros.commons.data.Constants.PREFS_FILE_NAME
import com.lucas.petros.commons.utils.CryptoManager
import com.lucas.petros.commons.utils.CryptoUtils
import com.lucas.petros.commons.utils.SecureTokenManager
import com.lucas.petros.network.getRetrofit
import com.lucas.petros.network.getRetrofitAuth
import com.lucas.petros.spotfylab.features.artists.data.remote.service.ArtistsApi
import com.lucas.petros.spotfylab.features.login.data.remote.service.LoginApi
import com.lucas.petros.spotfylab.features.artists.data.repository.ArtistsRepository
import com.lucas.petros.spotfylab.features.artists.data.repository.IArtistsRepository
import com.lucas.petros.spotfylab.features.login.data.repository.LoginRepository
import com.lucas.petros.spotfylab.features.login.data.repository.ILoginRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideRetrofitLogin(): LoginApi {
        return getRetrofitAuth().create(LoginApi::class.java)
    }

    @Provides
    @Singleton
    fun provideRetrofitArtists(): ArtistsApi {
        return getRetrofit().create(ArtistsApi::class.java)
    }

    @Provides
    @Singleton
    fun provideSecureTokenManager(sharedPreferences: SharedPreferences, cryptoManager: CryptoManager): SecureTokenManager {
        return SecureTokenManager(sharedPreferences,cryptoManager)
    }

    @Provides
    @Singleton
    fun provideSharedPreferences(@ApplicationContext context: Context): SharedPreferences {
        return context.getSharedPreferences(
            PREFS_FILE_NAME,
            Context.MODE_PRIVATE
        )
    }

    @Provides
    @Singleton
    fun provideCryptoManager(): CryptoManager {
        return CryptoUtils()
    }

    @Provides
    @Singleton
    fun provideLoginRepository(api: LoginApi): LoginRepository {
        return ILoginRepository(api)
    }

    @Provides
    @Singleton
    fun provideArtistsRepository(api: ArtistsApi): ArtistsRepository {
        return IArtistsRepository(api)
    }


}