package com.dev.rickandmortydeadoralive.utils.filterTypes

enum class FilterEnum(val filterType: FilterType) {

    STATUS_FILTER(FilterType(arrayOf("alive", "dead", "unknown", FilterType.WITHOUT_FILTERING))),
    GENDER_FILTER(FilterType(arrayOf("female", "male", "genderless", "unknown", FilterType.WITHOUT_FILTERING)))
}