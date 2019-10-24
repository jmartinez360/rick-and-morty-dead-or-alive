package com.dev.rickandmortydeadoralive.utils

import com.dev.rickandmortydeadoralive.R
import com.dev.rickandmortydeadoralive.models.Character

object GenderUtils {

    fun getGenderDrawable(gender: String): Int {
        return when (gender) {
            Character.FEMALE_GENDER -> R.drawable.ic_female_gender

            Character.MALE_GENDER -> R.drawable.ic_male_gender

            Character.GENDERLESS -> R.drawable.ic_genderless_gender

            else -> R.drawable.ic_unknown_gender
        }
    }
}