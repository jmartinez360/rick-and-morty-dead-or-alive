package com.dev.rickandmortydeadoralive.ui.activities

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dev.rickandmortydeadoralive.R
import com.dev.rickandmortydeadoralive.models.Character
import kotlinx.android.synthetic.main.activity_character_detail.*

class CharacterDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_character_detail)

        val characterModel: Character =  intent.getSerializableExtra(CHARACTER_MODEL_EXTRA) as Character


        characterId.text =  "No. ${characterModel.id}"
        characterName.text = characterModel.name
        charachterImage.setImageURI(Uri.parse(characterModel.image))
        specie.text = characterModel.species
        origin.text = characterModel.origin.name

    }

    companion object {
        const val CHARACTER_MODEL_EXTRA = "CHARACTER_MODEL"

        fun getIntent(character: Character, context: Context): Intent {
            val intent = Intent(context, CharacterDetailActivity::class.java)
            intent.putExtra(CHARACTER_MODEL_EXTRA, character)
            return intent
        }

    }
}
