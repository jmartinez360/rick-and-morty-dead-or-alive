package com.dev.rickandmortydeadoralive.ui.adapters

import androidx.recyclerview.widget.DiffUtil
import com.dev.rickandmortydeadoralive.models.Character

class CharacterListDiffUtilCallback(private val oldItems: List<Character>, private val newItems: List<Character>) :
    DiffUtil.Callback() {

    override fun getOldListSize(): Int = if (oldItems.isNullOrEmpty()) 0 else oldItems.size

    override fun getNewListSize(): Int = if (newItems.isNullOrEmpty()) 0 else newItems.size

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
       return oldItems[oldItemPosition] == newItems[newItemPosition]
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
       return oldItems[oldItemPosition].id == newItems[newItemPosition].id
    }
}