package com.dev.rickandmortydeadoralive.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.dev.rickandmortydeadoralive.R
import com.dev.rickandmortydeadoralive.models.Character
import com.dev.rickandmortydeadoralive.ui.adapters.listeners.CardClickListener
import com.dev.rickandmortydeadoralive.ui.adapters.listeners.ItemTouchAdapter
import com.dev.rickandmortydeadoralive.ui.adapters.listeners.CardListener
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*

class CharacterCardsAdapter constructor(private val dragStartListener: CardListener) : RecyclerView.Adapter<CardBindable>(), ItemTouchAdapter {

    private val ioScope = CoroutineScope(Dispatchers.IO)
    private val uiScope = CoroutineScope(Dispatchers.Main)

    var items : List<Character> = emptyList()
    set(newItems) {
        ioScope.launch {
            val result = update(field, newItems)
            uiScope.launch {
                print(result)
                field = newItems
            }
        }
    }

    private fun print(result: DiffUtil.DiffResult)  {
        result.dispatchUpdatesTo(this)
        notifyDataSetChanged()

    }

    private suspend   fun update(oldList: List<Character>, newList: List<Character>) : DiffUtil.DiffResult = withContext(Dispatchers.IO)  {
          DiffUtil.calculateDiff(CharacterListDiffUtilCallback(oldList, newList))
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