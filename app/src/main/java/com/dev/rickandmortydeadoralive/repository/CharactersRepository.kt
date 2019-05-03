package com.dev.rickandmortydeadoralive.repository

import com.dev.rickandmortydeadoralive.models.Character
import io.reactivex.Observable

interface CharactersRepository {

    fun getCharacters(charactersIds: List<Int>): Observable<List<Character>>

}