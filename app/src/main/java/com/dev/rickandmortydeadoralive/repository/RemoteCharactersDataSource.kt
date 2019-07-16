package com.dev.rickandmortydeadoralive.repository

import com.dev.rickandmortydeadoralive.api.ApiResult
import com.dev.rickandmortydeadoralive.api.CharactersApiServiceInterface
import com.dev.rickandmortydeadoralive.models.AllCharactersResult
import com.dev.rickandmortydeadoralive.models.Character
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.io.IOException
import javax.inject.Inject

class RemoteCharactersDataSource @Inject constructor(private val charactersApiServiceInterface: CharactersApiServiceInterface) :
    CharacterDataSource {

    override suspend fun getCharactersSuspended(options: Map<String, String>): ApiResult<AllCharactersResult> {
        return safeApiCall(call = {
            val response = charactersApiServiceInterface.getCharactersAsync(options).await()
            if (response.isSuccessful) {
                ApiResult.Success(response.body()!!)
            } else {
                ApiResult.Error(IOException(response.message()))
            }
        }, errorMessage = "error")
    }

    override fun getCharacters(charactersIds: List<Int>): Observable<List<Character>> {
        return charactersApiServiceInterface.getCharacters(charactersIds)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
    }
}