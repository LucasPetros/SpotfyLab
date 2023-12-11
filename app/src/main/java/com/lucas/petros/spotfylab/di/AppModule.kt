package com.lucas.petros.spotfylab.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import com.lucas.petros.commons.utils.CryptoManager
import com.lucas.petros.commons.utils.CryptoUtils
import com.lucas.petros.commons.utils.Prefs
import com.lucas.petros.commons.utils.Prefs.Companion.PREFS_FILE_NAME
import com.lucas.petros.network.getRetrofit
import com.lucas.petros.spotfylab.data.data_source.AppDatabase
import com.lucas.petros.spotfylab.features.artists.data.local.ArtistsDao
import com.lucas.petros.spotfylab.features.artists.data.remote.service.ArtistsApi
import com.lucas.petros.spotfylab.features.artists.data.repository.ArtistsRepository
import com.lucas.petros.spotfylab.features.artists.data.repository.IArtistsRepository
import com.lucas.petros.spotfylab.features.login.data.remote.service.LoginApi
import com.lucas.petros.spotfylab.features.login.data.repository.ILoginRepository
import com.lucas.petros.spotfylab.features.login.data.repository.LoginRepository
import com.lucas.petros.spotfylab.features.playlists.data.local.PlaylistsDao
import com.lucas.petros.spotfylab.features.playlists.data.remote.service.PlaylistsApi
import com.lucas.petros.spotfylab.features.playlists.data.repository.IPlaylistsRepository
import com.lucas.petros.spotfylab.features.playlists.data.repository.PlaylistsRepository
import com.lucas.petros.spotfylab.features.profile.data.local.ProfileDao
import com.lucas.petros.spotfylab.features.profile.data.remote.service.ProfileApi
import com.lucas.petros.spotfylab.features.profile.data.repository.IProfileRepository
import com.lucas.petros.spotfylab.features.profile.data.repository.ProfileRepository
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
    fun provideContext(@ApplicationContext app: Application): Context {
        return app
    }

    @Provides
    @Singleton
    fun provideRetrofitLogin(): LoginApi {
        return getRetrofit().create(LoginApi::class.java)
    }

    @Provides
    @Singleton
    fun provideRetrofitArtists(): ArtistsApi {
        return getRetrofit().create(ArtistsApi::class.java)
    }

    @Provides
    @Singleton
    fun provideRetrofitPlaylists(): PlaylistsApi {
        return getRetrofit().create(PlaylistsApi::class.java)
    }

    @Provides
    @Singleton
    fun provideRetrofitProfile(): ProfileApi {
        return getRetrofit().create(ProfileApi::class.java)
    }

    @Provides
    @Singleton
    fun provideAppDatabase(app: Application): AppDatabase {
        return Room.databaseBuilder(
            app, AppDatabase::class.java, AppDatabase.DATABASE_NAME
        ).build()
    }

    @Provides
    fun provideLoginDao(database: AppDatabase): ProfileDao {
        return database.profileDao()
    }

    @Provides
    fun provideArtistsDao(database: AppDatabase): ArtistsDao {
        return database.artistsDao()
    }

    @Provides
    fun providePlaylistsDao(database: AppDatabase): PlaylistsDao {
        return database.playlistsDao()
    }

    @Provides
    @Singleton
    fun provideSecureTokenManager(
        sharedPreferences: SharedPreferences,
        cryptoManager: CryptoManager
    ): Prefs {
        return Prefs(sharedPreferences, cryptoManager)
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
    fun provideArtistsRepository(api: ArtistsApi, artistsDao: ArtistsDao): ArtistsRepository {
        return IArtistsRepository(api, artistsDao)
    }

    @Provides
    @Singleton
    fun provideProfileRepository(api: ProfileApi, profileDao: ProfileDao): ProfileRepository {
        return IProfileRepository(api, profileDao)
    }

    @Provides
    @Singleton
    fun providePlaylistsRepository(
        api: PlaylistsApi,
        playlistsDao: PlaylistsDao
    ): PlaylistsRepository {
        return IPlaylistsRepository(api, playlistsDao)
    }
}