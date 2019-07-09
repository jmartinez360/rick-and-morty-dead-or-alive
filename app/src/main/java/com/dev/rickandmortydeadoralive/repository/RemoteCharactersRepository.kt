package com.dev.rickandmortydeadoralive.repository

import com.dev.rickandmortydeadoralive.api.ApiResult
import com.dev.rickandmortydeadoralive.models.AllCharactersResult
import com.dev.rickandmortydeadoralive.models.Character
import io.reactivex.Observable
import javax.inject.Inject

class RemoteCharactersRepository @Inject constructor(var remoteCharactersDataSource: RemoteCharactersDataSource): CharactersRepository {

    override suspend fun getSuspendedCharacters(options: Map<String, String>): ApiResult<AllCharactersResult> {
        return remoteCharactersDataSource.getCharactersSuspended(options)

    }

    override fun getCharacters(charactersIds: List<Int>): Observable<List<Character>> {
        return remoteCharactersDataSource.getCharacters(charactersIds)

    }
}