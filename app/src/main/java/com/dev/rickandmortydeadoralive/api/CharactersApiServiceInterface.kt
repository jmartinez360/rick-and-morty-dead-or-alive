package com.dev.rickandmortydeadoralive.api

import com.dev.rickandmortydeadoralive.models.Character
import com.dev.rickandmortydeadoralive.utils.Constants
import io.reactivex.Observable
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

interface CharactersApiServiceInterface {

    @GET("character/{charachtersIds}")
    fun getCharacters(@Path("charachtersIds") ids:List<Int>): Observable<List<Character>>

    companion object {
        fun create(): CharactersApiServiceInterface {
            val retrofit = retrofit2.Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(Constants.RICK_AND_MORTY_BASE_URL)
                .build()

            return retrofit.create(CharactersApiServiceInterface::class.java)
        }
    }
}