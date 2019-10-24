package com.dev.rickandmortydeadoralive.repository

import com.dev.rickandmortydeadoralive.api.ApiResult
import com.dev.rickandmortydeadoralive.models.AllCharactersResult
import com.dev.rickandmortydeadoralive.models.Character
import io.reactivex.Observable

interface CharacterDataSource {

    fun getCharacters(charactersIds: List<Int>): Observable<List<Character>>

    suspend fun getCharactersSuspended(options: Map<String, String>): ApiResult<AllCharactersResult>

}