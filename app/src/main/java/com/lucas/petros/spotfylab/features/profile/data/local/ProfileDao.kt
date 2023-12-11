package com.lucas.petros.spotfylab.features.profile.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.lucas.petros.spotfylab.features.profile.data.local.entity.UserProfileEntity

@Dao
interface ProfileDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveInfoUser(userProfile: UserProfileEntity)

    @Query("SELECT * FROM UserProfile")
    suspend fun getInfoUser(): UserProfileEntity
}