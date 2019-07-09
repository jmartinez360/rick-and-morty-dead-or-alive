package com.dev.rickandmortydeadoralive.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.dev.rickandmortydeadoralive.R
import com.dev.rickandmortydeadoralive.models.Character
import com.dev.rickandmortydeadoralive.ui.adapters.listeners.CardClickListener
import com.dev.rickandmortydeadoralive.ui.adapters.listeners.ItemTouchAdapter
import com.dev.rickandmortydeadoralive.ui.adapters.listeners.OnStartDragListener
import java.util.*

class CharacterCardsAdapter constructor(private val clickListener: CardClickListener, private val dragStartListener: OnStartDragListener) : RecyclerView.Adapter<CardBindable>(), ItemTouchAdapter {

    var items : List<Character> = emptyList()
    set(newItems) {
        val result = DiffUtil.calculateDiff(CharacterListDiffUtilCallback(field, newItems))
        result.dispatchUpdatesTo(this)
        field = newItems
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardBindable {
        return CharacterBasicCardViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.card_grid_item, parent, false), dragStartListener)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: CardBindable, position: Int) {
        holder.bindItem(items[position], holder)
    }

    override fun onItemMove(fromPosition: Int, toPosition: Int): Boolean {
        Collections.swap(items, fromPosition, toPosition)
        notifyItemMoved(fromPosition, toPosition)
        return true
    }
}