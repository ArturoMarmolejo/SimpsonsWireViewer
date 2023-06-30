package com.sample.simpsonsviewer.model.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sample.simpsonsviewer.model.local.entities.CharacterEntity

@Dao
interface SimpsonsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCharacters(character: List<CharacterEntity>?)

    @Query("SELECT * from characters")
    suspend fun getAllLocalCharacters(): List<CharacterEntity>
}