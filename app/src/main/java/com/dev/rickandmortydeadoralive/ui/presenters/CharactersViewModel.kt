package com.dev.rickandmortydeadoralive.ui.presenters

import android.util.Log
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dev.rickandmortydeadoralive.api.ApiResult
import com.dev.rickandmortydeadoralive.models.AllCharactersResult
import com.dev.rickandmortydeadoralive.models.Character
import com.dev.rickandmortydeadoralive.repository.RemoteCharactersRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class CharactersViewModel @Inject constructor (private val characterRepository: RemoteCharactersRepository): ViewModel(),  LifecycleObserver {

    companion object {
        private val PAGINATION_REGEX = "(?<=page=)\\d+".toRegex()
    }

    private val scope = CoroutineScope(Dispatchers.IO)
    private var nextPagination: String = ""

    var allCharacters = ArrayList<Character>()


    var characterListToPrint = MutableLiveData<List<Character>>()
    var isLoading = MutableLiveData<Boolean>()
    var isLastPage = MutableLiveData<Boolean>()
    var errorLiveData = MutableLiveData<String>()

    fun loadCharacters() {
        isLastPage.postValue(false)
        loadCharacters(emptyMap())
    }

    fun loadCharacters(options: Map<String, String>) {
        isLoading.postValue(true)
        scope.launch {
            val value = characterRepository.getSuspendedCharacters(options)
            when (value) {
                is ApiResult.Success -> {
                    printCharacterList(value.data.results)
                    getNextPage(value)
                }
                is ApiResult.Error -> errorLiveData.postValue(value.exception.message)
            }
        }
    }

    fun nextPage() {
        Log.d("pagina", "cargo siguiente pagina con valor de $nextPagination")
        val mutableMap = mapOf("page" to nextPagination)
        loadCharacters(mutableMap)
    }

    private fun getNextPage(value: ApiResult.Success<AllCharactersResult>) {
        val matchResult = PAGINATION_REGEX.find(value.data.info.next)
        nextPagination = matchResult?.value!!
    }

    fun filterInLocal(filter: String) {
        scope.launch {
            characterListToPrint.postValue(allCharacters.filter { item -> item.status.toUpperCase() == filter.toUpperCase() })
        }
    }

    private fun printCharacterList(value: List<Character>) {
        allCharacters.addAll(value)
        characterListToPrint.postValue(allCharacters)
        isLoading.postValue(false)
    }

    fun restart() {
        loadCharacters()
    }

}