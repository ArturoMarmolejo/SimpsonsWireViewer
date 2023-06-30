package com.sample.simpsonsviewer.model.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.sample.simpsonsviewer.model.local.entities.CharacterEntity


@Database(
    entities = [
        CharacterEntity::class
    ],
    version = 2
)
abstract class SimpsonsDatabase: RoomDatabase() {
    abstract val simpsonsDao: SimpsonsDao
}