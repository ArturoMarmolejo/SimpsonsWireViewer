package com.sample.simpsonsviewer.usecases

import android.util.Log
import com.sample.simpsonsviewer.model.local.LocalRepository
import com.sample.simpsonsviewer.model.localmodel.CharacterModel
import com.sample.simpsonsviewer.rest.SimpsonsRepository
import com.sample.simpsonsviewer.rest.SimpsonsServiceApi
import com.sample.simpsonsviewer.utils.NetworkState
import com.sample.simpsonsviewer.utils.UIState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

import java.lang.Exception
import javax.inject.Inject

private const val TAG = "GetAllSimpsonsCharacter"
class GetAllSimpsonsCharactersUseCase @Inject constructor(
    private val simpsonsServiceApi: SimpsonsServiceApi,
    private val localRepository: LocalRepository,
    private val simpsonsRepository: SimpsonsRepository,
    private val networkState: NetworkState,
) {
    operator fun invoke(): Flow<UIState<List<CharacterModel>>> {
        Log.d(TAG, "invoke: GetAllCharacters Use case")
        return if(!networkState.isInternetOn()){
            Log.d(TAG, "invoke: Get All LOCAL CHARACTERS")
            localRepository.getAllLocalCharacters()
        } else {
            Log.d(TAG, "invoke: Get All API CHARACTERS")
            simpsonsRepository.getAllSimpsonsCharacters()
        }
    }
}