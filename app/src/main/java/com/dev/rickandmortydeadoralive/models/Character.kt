package com.dev.rickandmortydeadoralive.models

import java.io.Serializable

data class Character(val id: Int, val name: String, val species: String, val status: String, val image: String, val origin : Origin, val gender: String): Serializable {
    companion object {
        const val ALIVE = "Alive"
        const val DEAD = "Dead"
        const val UNKNOWN = "unknown"
        const val STATUS_FILTER =  "status"
        const val GENDER_FILTER =  "gender"
        const val NAME_FILTER =  "name"
        const val FEMALE_GENDER = "Female"
        const val MALE_GENDER = "Male"
        const val GENDERLESS = "Genderless"
    }
}

data class Origin(val name: String): Serializable

data class Pagination(val next: String)

data class AllCharactersResult(val info: Pagination, val results: List<Character>)