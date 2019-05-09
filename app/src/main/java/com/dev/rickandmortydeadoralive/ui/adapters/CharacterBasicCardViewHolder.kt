package com.dev.rickandmortydeadoralive.ui.adapters

import android.view.View
import com.dev.rickandmortydeadoralive.models.Character
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.card_item.view.*

class CharacterBasicCardViewHolder internal constructor(itemView: View) : CardBindable(itemView) {

    private lateinit var characterItem: Character

    override fun bindItem(character: Character) {
        this.characterItem = character

        itemView.characterId.text = "No. " + characterItem.id
        itemView.characterName.text = characterItem.name
        itemView.specie.text = characterItem.species
        itemView.origin.text = characterItem.origin.name

        Picasso.get().load(character.image).into(itemView.charachterImage)

    }
}