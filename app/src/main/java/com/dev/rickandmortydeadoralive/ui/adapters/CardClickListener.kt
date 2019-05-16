package com.dev.rickandmortydeadoralive.ui.adapters

import com.dev.rickandmortydeadoralive.models.Character

interface CardClickListener {

    fun onDeadClickListener(item: Character)

    fun onAliveClickListener(item: Character)

    fun onUnknownClickListener(item: Character)
}