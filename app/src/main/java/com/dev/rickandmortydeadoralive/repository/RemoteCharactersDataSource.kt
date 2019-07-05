package com.dev.rickandmortydeadoralive.repository

import com.dev.rickandmortydeadoralive.api.ApiResult
import com.dev.rickandmortydeadoralive.api.CharactersApiServiceInterface
import com.dev.rickandmortydeadoralive.models.Character
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.io.IOException
import javax.inject.Inject

class RemoteCharactersDataSource @Inject constructor(var charactersApiServiceInterface: CharactersApiServiceInterface) :
    CharacterDataSource {

    override suspend fun getCharactersSuspended(charactersIds: List<Int>): ApiResult<List<Character>> {
        return safeApiCall(call = {
            val response = charactersApiServiceInterface.getCharactersSuspended(charactersIds).await()
            if (response.isSuccessful) {
                ApiResult.Success(response.body()!!)
            } else {
                ApiResult.Error(IOException("Error consultando el endpoint"))
            }
        }, errorMessage = "error")

    }

    override fun getCharacters(charactersIds: List<Int>): Observable<List<Character>> {
        return charactersApiServiceInterface.getCharacters(charactersIds)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
    }
}