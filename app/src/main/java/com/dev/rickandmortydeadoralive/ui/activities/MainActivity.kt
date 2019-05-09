package com.dev.rickandmortydeadoralive.ui.activities

import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.LinearInterpolator
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.dev.rickandmortydeadoralive.Injector
import com.dev.rickandmortydeadoralive.R
import com.dev.rickandmortydeadoralive.models.Character
import com.dev.rickandmortydeadoralive.ui.adapters.CharacterCardsAdapter
import com.dev.rickandmortydeadoralive.ui.presenters.CharactersDeckPresenter
import com.dev.rickandmortydeadoralive.ui.views.CharactersDeckView
import com.dev.rickandmortydeadoralive.utils.cardColors.CardType
import com.dev.rickandmortydeadoralive.utils.cardColors.GradientCardColor
import com.yuyakaido.android.cardstackview.*
import javax.inject.Inject

class MainActivity : AppCompatActivity(), CharactersDeckView, CardStackListener {

    @Inject
    lateinit var presenter: CharactersDeckPresenter

    private val adapter by lazy { CharacterCardsAdapter() }
    private val recycler by lazy { findViewById<RecyclerView>(R.id.characterRecycler) }
    private val manager by lazy { CardStackLayoutManager(this, this) }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Injector.obtain(this)!!.inject(this)
        init()

        val gradientCardType = CardType.ZINC_CARD.cardColor as GradientCardColor

        presenter.view = this
        presenter.loadCharacters()


       /* val characterId = findViewById<TextView>(R.id.character_id)
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
        animationDrawable.start() */
    }

    fun init() {
        manager.setStackFrom(StackFrom.None)
        manager.setVisibleCount(3)
        manager.setTranslationInterval(8.0f)
        manager.setScaleInterval(0.95f)
        manager.setSwipeThreshold(0.3f)
        manager.setMaxDegree(20.0f)
        manager.setDirections(Direction.HORIZONTAL)
        manager.setCanScrollHorizontal(true)
        manager.setCanScrollVertical(true)
        manager.setSwipeableMethod(SwipeableMethod.AutomaticAndManual)
        manager.setOverlayInterpolator(LinearInterpolator())

        recycler.layoutManager = manager
        recycler.adapter = adapter
    }

    override fun showCharacters(characterList: List<Character>) {
        adapter.items = characterList
        adapter.notifyDataSetChanged()

    }

    override fun onCardDragging(direction: Direction?, ratio: Float) {
    }

    override fun onCardSwiped(direction: Direction?) {
    }

    override fun onCardCanceled() {
    }

    override fun onCardAppeared(view: View?, position: Int) {
    }

    override fun onCardRewound() {
    }

    override fun onCardDisappeared(view: View?, position: Int) {
        Log.d("rick and morty", "Acabo de esconder la carta de " + adapter.items!![position])
    }
}
