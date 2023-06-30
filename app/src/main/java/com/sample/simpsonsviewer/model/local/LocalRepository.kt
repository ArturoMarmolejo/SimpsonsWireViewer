package com.sample.simpsonsviewer.model.local

import android.util.Log
import com.sample.simpsonsviewer.model.local.entities.CharacterEntity
import com.sample.simpsonsviewer.model.localmodel.CharacterModel
import com.sample.simpsonsviewer.model.localmodel.mapFromEntityToCharacter
import com.sample.simpsonsviewer.utils.UIState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

private const val TAG = "LocalRepository"
interface LocalRepository {
    suspend fun insertCharacters(characters: List<CharacterEntity>?)
    fun getAllLocalCharacters(): Flow<UIState<List<CharacterModel>>>
}

class LocalRepositoryImpl(
    private val simpsonsDao: SimpsonsDao
): LocalRepository {
    override suspend fun insertCharacters(characters: List<CharacterEntity>?) {
        simpsonsDao.insertCharacters(characters)
    }

    override fun getAllLocalCharacters(): Flow<UIState<List<CharacterModel>>> = flow {
        try {

            val characterInfoList = simpsonsDao.getAllLocalCharacters()
            Log.d(TAG, "getAllLocalCharacters: Fetching data from the database $characterInfoList")
            emit(UIState.SUCCESS(characterInfoList.mapFromEntityToCharacter()))
        } catch (e: Exception){
            emit(UIState.ERROR(e))
        }
    }
}