package com.dev.rickandmortydeadoralive.ui.adapters

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.dev.rickandmortydeadoralive.models.Character
import com.dev.rickandmortydeadoralive.ui.adapters.listeners.ItemTouchViewHolder
import com.dev.rickandmortydeadoralive.ui.adapters.listeners.OnStartDragListener
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.card_item.view.*


class CharacterBasicCardViewHolder internal constructor(itemView: View, private val onDragListener: OnStartDragListener) : CardBindable(itemView), ItemTouchViewHolder {

    private lateinit var characterItem: Character

    override fun bindItem(character: Character, viewHolder: RecyclerView.ViewHolder) {
        this.characterItem = character

        itemView.characterId.text = "No. " + characterItem.id
        Picasso.get().load(character.image).into(itemView.charachterImage)

        itemView.setOnLongClickListener {
            onDragListener.onStartDrag(viewHolder)
            true
        }
    }

    override fun onItemSelected() {
        //MOSTRAR ALGO CUANDO SE SELECCIONA
    }

    override fun onItemClear() {
        //LIMPIAR LA VISTA
    }
}