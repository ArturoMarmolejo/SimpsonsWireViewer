package com.sample.simpsonsviewer.viewmodel

import android.provider.Contacts.Intents.UI
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sample.simpsonsviewer.model.apimodel.RelatedTopic
import com.sample.simpsonsviewer.model.localmodel.CharacterModel
import com.sample.simpsonsviewer.usecases.GetAllSimpsonsCharactersUseCase
import com.sample.simpsonsviewer.utils.UIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SimpsonsViewModel @Inject constructor(
    private val getAllSimpsonsCharactersUseCase: GetAllSimpsonsCharactersUseCase,
    private val ioDispatcher: CoroutineDispatcher
): ViewModel() {
    private var isInitialized = false
    lateinit var selectedSimpsonsCharacter: CharacterModel

    private val _allCharacters: MutableLiveData<UIState<List<CharacterModel>>> = MutableLiveData(UIState.LOADING)
    val allCharacters: LiveData<UIState<List<CharacterModel>>> get() = _allCharacters

    init {
        if(!isInitialized){
            getAllCharacters()
            isInitialized = true
        }
    }

    fun getAllCharacters() {
        viewModelScope.launch(ioDispatcher) {
            getAllSimpsonsCharactersUseCase().collect{ result ->
                _allCharacters.postValue(result)
            }
        }
    }
}
