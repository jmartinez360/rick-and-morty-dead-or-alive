package com.dev.rickandmortydeadoralive.api

import com.dev.rickandmortydeadoralive.models.Character
import io.reactivex.Observable
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface CharactersApiServiceInterface {

    @GET("character/{charachtersIds}")
    fun getCharacters(@Path("charachtersIds") ids:List<Int>): Observable<List<Character>>

    @GET("character/{charachtersIds}")
    fun getCharactersSuspended(@Path("charachtersIds") ids:List<Int>): Deferred<Response<List<Character>>>

}