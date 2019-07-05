package com.dev.rickandmortydeadoralive.repository

import com.dev.rickandmortydeadoralive.api.ApiResult
import com.dev.rickandmortydeadoralive.models.Character
import io.reactivex.Observable
import javax.inject.Inject

class RemoteCharactersRepository @Inject constructor(var remoteCharactersDataSource: RemoteCharactersDataSource): CharactersRepository {

    override suspend fun getSuspendedCharacters(charactersIds: List<Int>): ApiResult<List<Character>> {
        return remoteCharactersDataSource.getCharactersSuspended(charactersIds)
    }

    override fun getCharacters(charactersIds: List<Int>): Observable<List<Character>> {
        return remoteCharactersDataSource.getCharacters(charactersIds)

    }
}