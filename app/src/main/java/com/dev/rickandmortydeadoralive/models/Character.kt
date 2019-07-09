package com.dev.rickandmortydeadoralive.models

data class Character(val id: Int, val name: String, val species: String, val status: String, val image: String, val origin : Origin) {
    companion object {
        val ALIVE = "Alive"
        val DEAD = "Dead"
        val UNKNOWN = "unknown"
    }
}

data class Origin(val name: String)

data class Pagination(val next: String)

data class AllCharactersResult(val info: Pagination, val results: List<Character>)