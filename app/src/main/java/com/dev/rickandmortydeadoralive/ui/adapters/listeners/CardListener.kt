package com.dev.rickandmortydeadoralive.ui.adapters.listeners

import androidx.recyclerview.widget.RecyclerView
import com.dev.rickandmortydeadoralive.models.Character

interface CardListener {

    fun onStartDrag(viewHolder: RecyclerView.ViewHolder)

    fun onCharacterClickListener(character: Character)

}