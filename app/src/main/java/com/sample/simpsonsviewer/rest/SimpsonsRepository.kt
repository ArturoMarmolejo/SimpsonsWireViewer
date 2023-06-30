package com.sample.simpsonsviewer.rest

import android.util.Log
import com.sample.simpsonsviewer.model.local.SimpsonsDao
import com.sample.simpsonsviewer.model.local.entities.mapToCharacterEntity
import com.sample.simpsonsviewer.model.localmodel.CharacterModel
import com.sample.simpsonsviewer.model.localmodel.mapFromEntityToCharacter
import com.sample.simpsonsviewer.usecases.GetAllSimpsonsCharactersUseCase
import com.sample.simpsonsviewer.utils.FailureResponse
import com.sample.simpsonsviewer.utils.NullSimpsonsCharacterListResponse
import com.sample.simpsonsviewer.utils.UIState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.lang.Exception
import javax.inject.Inject

private const val TAG = "SimpsonsRepository"
interface SimpsonsRepository {
     fun getAllSimpsonsCharacters(): Flow<UIState<List<CharacterModel>>>
}

class SimpsonsRepositoryImpl @Inject constructor(
   private val simpsonsServiceApi: SimpsonsServiceApi,
   private val simpsonsDao: SimpsonsDao
): SimpsonsRepository {
    override fun getAllSimpsonsCharacters(): Flow<UIState<List<CharacterModel>>> = flow {
        emit(UIState.LOADING)
        try {
            val response = simpsonsServiceApi.getAllSimpsonsCharacters()
            if(response.isSuccessful) {
                response.body()?.let {
                    val characterInfoList = it.relatedTopics
                    Log.d(TAG, "getAllSimpsonsCharacters: Inserting characters in database, $characterInfoList")
                    simpsonsDao.insertCharacters(characterInfoList.mapToCharacterEntity())
                    val newCharacterInfoList = simpsonsDao.getAllLocalCharacters()
                    Log.d(TAG, "getAllSimpsonsCharacters: Get all Characters SUCCESS $newCharacterInfoList")
                    emit(UIState.SUCCESS(newCharacterInfoList.mapFromEntityToCharacter()))
                } ?: throw NullSimpsonsCharacterListResponse()
            } else {
                throw FailureResponse(response.errorBody()?.string())
            }
        } catch (e: Exception) {
            emit(UIState.ERROR(e))
        }
    }
}