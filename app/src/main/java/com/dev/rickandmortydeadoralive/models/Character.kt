package com.dev.rickandmortydeadoralive.models

data class Character(val id: Int, val name: String, val species: String, val status: String, val image: String, val origin : Origin) {
    companion object {
        const val ALIVE = "Alive"
        const val DEAD = "Dead"
        const val UNKNOWN = "unknown"
        const val STATUS_FILTER =  "status"
        const val GENDER_FILTER =  "gender"
        const val NAME_FILTER =  "name"
    }
}

data class Origin(val name: String)

data class Pagination(val next: String)

data class AllCharactersResult(val info: Pagination, val results: List<Character>)