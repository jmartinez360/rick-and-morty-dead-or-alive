package com.dev.rickandmortydeadoralive.ui.activities

import android.graphics.Color
import android.graphics.drawable.AnimationDrawable
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.util.Log
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.dev.rickandmortydeadoralive.Injector
import com.dev.rickandmortydeadoralive.R
import com.dev.rickandmortydeadoralive.models.Character
import com.dev.rickandmortydeadoralive.ui.presenters.CharactersDeckPresenter
import com.dev.rickandmortydeadoralive.ui.views.CharactersDeckView
import com.dev.rickandmortydeadoralive.utils.cardColors.CardType
import com.dev.rickandmortydeadoralive.utils.cardColors.GradientCardColor
import javax.inject.Inject


class MainActivity : AppCompatActivity(), CharactersDeckView {

    @Inject
    lateinit var presenter: CharactersDeckPresenter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Injector.obtain(this)!!.inject(this)

        val gradientCardType = CardType.GRADIENT_CARD.cardColor as GradientCardColor

        presenter.view = this
        presenter.loadCharacters()

        val characterId = findViewById<TextView>(R.id.character_id)
        val infoContainer = findViewById<LinearLayout>(R.id.info_container)
        val animationDrawable = AnimationDrawable()

        for (x in 0 until gradientCardType.bodyColor.size) {
            val gradientDrawable = GradientDrawable()

            var indexFirstColor = x
            var indexSecondColor = x + 1

            if (x == gradientCardType.bodyColor.size - 1) {
                indexFirstColor = x
                indexSecondColor = 0
            }

            gradientDrawable.colors = intArrayOf(
                Color.parseColor(gradientCardType.bodyColor[indexFirstColor]),
                Color.parseColor(gradientCardType.bodyColor[indexSecondColor])
            )

            gradientDrawable.gradientType = GradientDrawable.LINEAR_GRADIENT
            gradientDrawable.orientation = GradientDrawable.Orientation.BR_TL

            animationDrawable.addFrame(gradientDrawable, 2000)
        }

        characterId.background = animationDrawable
        infoContainer.background = animationDrawable
        animationDrawable.setEnterFadeDuration(2000)
        animationDrawable.setExitFadeDuration(2000)
        animationDrawable.start()
    }

    override fun showCharacters(characterList: List<Character>) {
        Log.d("rick and morty", characterList.toString())

    }
}
