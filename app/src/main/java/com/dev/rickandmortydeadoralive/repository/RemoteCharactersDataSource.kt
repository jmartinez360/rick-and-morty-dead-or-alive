package com.dev.rickandmortydeadoralive.repository

import com.dev.rickandmortydeadoralive.api.CharactersApiServiceInterface
import com.dev.rickandmortydeadoralive.models.Character
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class RemoteCharactersDataSource @Inject constructor(var charactersApiServiceInterface: CharactersApiServiceInterface) :
    CharacterDataSource {

    override fun getCharacters(charactersIds: List<Int>): Observable<List<Character>> {
        return charactersApiServiceInterface.getCharacters(charactersIds)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
    }
}