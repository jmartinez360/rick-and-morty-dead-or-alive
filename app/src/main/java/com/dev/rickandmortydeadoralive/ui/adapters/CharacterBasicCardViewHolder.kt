package com.dev.rickandmortydeadoralive.ui.adapters

import android.graphics.Color
import android.view.View
import com.dev.rickandmortydeadoralive.models.Character
import com.dev.rickandmortydeadoralive.ui.adapters.listeners.CardClickListener
import com.dev.rickandmortydeadoralive.ui.adapters.listeners.ItemTouchViewHolder
import com.dev.rickandmortydeadoralive.ui.adapters.listeners.OnStartDragListener
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.card_item.view.*
import android.view.MotionEvent
import androidx.core.view.MotionEventCompat
import androidx.recyclerview.widget.RecyclerView


class CharacterBasicCardViewHolder internal constructor(itemView: View, private val onDragListener: OnStartDragListener) : CardBindable(itemView), ItemTouchViewHolder {

    private lateinit var characterItem: Character

    override fun bindItem(character: Character, viewHolder: RecyclerView.ViewHolder) {
        this.characterItem = character

        itemView.characterId.text = "No. " + characterItem.id
        Picasso.get().load(character.image).into(itemView.charachterImage)

        itemView.setOnLongClickListener{

            onDragListener.onStartDrag(viewHolder)

            true
        }

        /*itemView.setOnTouchListener(View.OnTouchListener { _, event ->
            if (MotionEventCompat.getActionMasked(event) == MotionEvent.ACTION_DOWN) {
                if (viewHolder != null) {
                onDragListener.onStartDrag(viewHolder)
                }
            }
            false
        }) */

    }

    override fun onItemSelected() {
        //MOSTRAR ALGO CUANDO SE SELECCIONA
    }

    override fun onItemClear() {
        //LIMPIAR LA VISTA
    }
}