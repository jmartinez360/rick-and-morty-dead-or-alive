package com.dev.rickandmortydeadoralive.utils

import kotlin.random.Random

class RandomUtils {

    companion object {

        private val MIN_ID = 1
        private val MAX_ID = 493

        fun getRandomNumberList(listSize: Int) : List<Int> {
            return  List(listSize) {Random.nextInt(MIN_ID, MAX_ID)}
        }
    }
}