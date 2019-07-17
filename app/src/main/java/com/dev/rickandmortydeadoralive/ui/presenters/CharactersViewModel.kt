package com.dev.rickandmortydeadoralive.ui.presenters

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dev.rickandmortydeadoralive.api.ApiResult
import com.dev.rickandmortydeadoralive.models.AllCharactersResult
import com.dev.rickandmortydeadoralive.models.Character
import com.dev.rickandmortydeadoralive.repository.RemoteCharactersRepository
import com.dev.rickandmortydeadoralive.utils.filterTypes.FilterType
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class CharactersViewModel @Inject constructor (private val characterRepository: RemoteCharactersRepository): ViewModel(),  LifecycleObserver {

    companion object {
        private val PAGINATION_REGEX = "(?<=page=)\\d+".toRegex()
    }

    private val scope = CoroutineScope(Dispatchers.IO)
    private var nextPagination: String? = null

    private val filters = mutableMapOf<String, String>()

    var allCharacters = ArrayList<Character>()
    var characterListToPrint = MutableLiveData<List<Character>>()
    var isLoading = MutableLiveData<Boolean>()
    var isLastPage = MutableLiveData<Boolean>()
    var errorLiveData = MutableLiveData<String>()
    var filtersLiveData = MutableLiveData<Map<String, String>>()


    fun loadCharacters(filteredName: String?, filteredGender: String?, filteredStatus: String?) {
        filters.remove("page")

        filteredName?.apply {
            when(this.isNotEmpty()) {
                true -> filters[Character.NAME_FILTER] = this
                else -> filters.remove(Character.NAME_FILTER)
            }
        }

        filteredGender?.apply {
            when (this) {
                FilterType.WITHOUT_FILTERING -> filters.remove(Character.GENDER_FILTER)
                else -> filters[Character.GENDER_FILTER] = this
            }
        }

        filteredStatus?.apply {
            when (this) {
                FilterType.WITHOUT_FILTERING -> filters.remove(Character.STATUS_FILTER)
                else -> filters[Character.STATUS_FILTER] = this
            }
        }

        filtersLiveData.postValue(filters)
        loadCharacters(options =  filters, isPaginating = false)
    }

    private fun loadCharacters(options: Map<String, String>, isPaginating: Boolean) {
        isLastPage.postValue(false)
        isLoading.postValue(true)
        scope.launch {
            val value = characterRepository.getSuspendedCharacters(options)
            when (value) {
                is ApiResult.Success -> {
                    printCharacterList(value.data.results, isPaginating)
                    getNextPage(value)
                }
                is ApiResult.Error -> errorLiveData.postValue(value.exception.message)
            }
        }
    }

    fun nextPage() {
        nextPagination?.let { filters["page"] = it }
        loadCharacters(options = filters, isPaginating = true)
    }

    private fun getNextPage(value: ApiResult.Success<AllCharactersResult>) {
        val matchResult = PAGINATION_REGEX.find(value.data.info.next)
        nextPagination = matchResult?.value
        if (nextPagination == null) {
            isLastPage.postValue(true)
        }
    }

    fun filterInLocal(filter: String) {
        scope.launch {
            characterListToPrint.postValue(allCharacters.filter { item -> item.status.toUpperCase() == filter.toUpperCase() })
        }
    }

    private fun printCharacterList(value: List<Character>, isPaginating: Boolean) {
        if (isPaginating) allCharacters.addAll(value) else allCharacters = ArrayList(value)
        characterListToPrint.postValue(allCharacters)
        isLoading.postValue(false)
    }

}