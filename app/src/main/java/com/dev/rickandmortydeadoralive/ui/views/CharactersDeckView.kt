package com.dev.rickandmortydeadoralive.ui.views

import com.dev.rickandmortydeadoralive.models.Character

interface CharactersDeckView: View {

    fun showCharacters(characterList: List<Character>)

    fun hideDeck()

    fun notifyWrongAnswer(direction: String)

    fun notifyLifes(lifesCounter: Int)
}