package com.dev.rickandmortydeadoralive.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dev.rickandmortydeadoralive.R
import com.dev.rickandmortydeadoralive.models.Character

class CharacterCardsAdapter : RecyclerView.Adapter<CardBindable>() {

    var items : List<Character>? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardBindable {
        return CharacterBasicCardViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.card_item, parent, false))
    }

    override fun getItemCount(): Int {
        return if (items == null) 0 else items!!.size
    }

    override fun onBindViewHolder(holder: CardBindable, position: Int) {
        holder.bindItem(items!![position])
    }

}