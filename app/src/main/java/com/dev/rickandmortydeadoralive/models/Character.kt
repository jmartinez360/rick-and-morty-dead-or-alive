package com.dev.rickandmortydeadoralive.models

data class Character(val id: Int, val name: String, val species: String, val status: String, val image: String, val origin : Origin)

data class Origin(val name: String)