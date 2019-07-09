package com.dev.rickandmortydeadoralive.ui.presenters

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dev.rickandmortydeadoralive.api.ApiResult
import com.dev.rickandmortydeadoralive.models.Character
import com.dev.rickandmortydeadoralive.repository.RemoteCharactersRepository
import com.dev.rickandmortydeadoralive.utils.RandomUtils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch
import javax.inject.Inject

class CharactersViewModel @Inject constructor (private val charaterRepository: RemoteCharactersRepository): ViewModel(),  LifecycleObserver {

    private val scope = CoroutineScope(Dispatchers.IO)

    var allCharacters = ArrayList<Character>()


    var characterListToPrint = MutableLiveData<List<Character>>()
    var errorLiveData = MutableLiveData<String>()

    fun loadCharacters() {
        scope.launch {
            val ids = RandomUtils.getRandomNumberList(listSize = 30)
            val value = charaterRepository.getSuspendedCharacters(ids)
            when (value) {
                is ApiResult.Success -> printCharacterList(value.data)
                is ApiResult.Error -> errorLiveData.postValue(value.exception.message)
            }
        }
    }

    fun filterInLocal(filter: String) {
        scope.launch {
            characterListToPrint.postValue(allCharacters.filter { item -> item.status.toUpperCase() == filter })
        }
    }

    private fun printCharacterList(value: List<Character>) {
        allCharacters.addAll(value)
        characterListToPrint.postValue(value)
    }

    fun restart() {
        loadCharacters()
    }

}