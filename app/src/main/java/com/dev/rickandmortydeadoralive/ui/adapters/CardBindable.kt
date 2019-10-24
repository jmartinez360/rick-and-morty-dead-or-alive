package com.dev.rickandmortydeadoralive.ui.adapters

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.dev.rickandmortydeadoralive.models.Character

abstract class CardBindable constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {

    abstract fun bindItem(character: Character, viewHolder: RecyclerView.ViewHolder)
}