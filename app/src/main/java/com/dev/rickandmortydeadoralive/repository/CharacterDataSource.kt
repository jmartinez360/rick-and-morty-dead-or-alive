package com.dev.rickandmortydeadoralive.repository

import com.dev.rickandmortydeadoralive.api.ApiResult
import com.dev.rickandmortydeadoralive.models.Character
import io.reactivex.Observable

interface CharacterDataSource {

    fun getCharacters(charactersIds: List<Int>): Observable<List<Character>>

    suspend fun getCharactersSuspended(charactersIds: List<Int>): ApiResult<List<Character>>

}