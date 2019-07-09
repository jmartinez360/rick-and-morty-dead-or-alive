package com.dev.rickandmortydeadoralive.api

import com.dev.rickandmortydeadoralive.models.AllCharactersResult
import com.dev.rickandmortydeadoralive.models.Character
import io.reactivex.Observable
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.QueryMap

interface CharactersApiServiceInterface {

    @GET("character/{charachtersIds}")
    fun getCharacters(@Path("charachtersIds") ids:List<Int>): Observable<List<Character>>

    @GET("character/{charachtersIds}")
    fun getCharactersAsync(@Path("charachtersIds") ids:List<Int>): Deferred<Response<List<Character>>>

    @GET("character")
    fun getCharactersAsync(@QueryMap options:Map <String, String>): Deferred<Response<AllCharactersResult>>

}