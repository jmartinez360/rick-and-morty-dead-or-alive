package com.dev.rickandmortydeadoralive.ui.adapters

import android.net.Uri
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.dev.rickandmortydeadoralive.models.Character
import com.dev.rickandmortydeadoralive.ui.adapters.listeners.CardListener
import com.dev.rickandmortydeadoralive.ui.adapters.listeners.ItemTouchViewHolder
import com.dev.rickandmortydeadoralive.utils.GenderUtils
import kotlinx.android.synthetic.main.card_grid_item.view.*


class CharacterBasicCardViewHolder internal constructor(itemView: View, private val onDragListener: CardListener) : CardBindable(itemView), ItemTouchViewHolder {

    private lateinit var characterItem: Character

    override fun bindItem(character: Character, viewHolder: RecyclerView.ViewHolder) {
        this.characterItem = character

        itemView.characterId.text = "No. " + characterItem.id
        itemView.charachterImage.setImageURI(Uri.parse(characterItem.image))

        itemView.setOnLongClickListener {
            onDragListener.onStartDrag(viewHolder)
            true
        }
        itemView.genderImage.setImageResource(GenderUtils.getGenderDrawable(characterItem.gender))

        itemView.setOnClickListener { onDragListener.onCharacterClickListener(characterItem) }
    }

    override fun onItemSelected() {
        //MOSTRAR ALGO CUANDO SE SELECCIONA
    }

    override fun onItemClear() {
        //LIMPIAR LA VISTA
    }
}