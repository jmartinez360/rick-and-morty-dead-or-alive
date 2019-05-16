package com.dev.rickandmortydeadoralive.ui.activities

import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.LinearInterpolator
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.dev.rickandmortydeadoralive.Injector
import com.dev.rickandmortydeadoralive.R
import com.dev.rickandmortydeadoralive.models.Character
import com.dev.rickandmortydeadoralive.ui.adapters.CharacterCardsAdapter
import com.dev.rickandmortydeadoralive.ui.presenters.CharactersDeckPresenter
import com.dev.rickandmortydeadoralive.ui.views.CharactersDeckView
import com.dev.rickandmortydeadoralive.utils.CustomDialog
import com.dev.rickandmortydeadoralive.utils.CustomDialogClickListener
import com.dev.rickandmortydeadoralive.utils.cardColors.CardType
import com.dev.rickandmortydeadoralive.utils.cardColors.GradientCardColor
import com.yuyakaido.android.cardstackview.*
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import javax.inject.Inject


class MainActivity : AppCompatActivity(), CharactersDeckView, CardStackListener {

    @Inject
    lateinit var presenter: CharactersDeckPresenter

    private val adapter by lazy { CharacterCardsAdapter() }
    private val recycler by lazy { findViewById<CardStackView>(R.id.characterRecycler) }
    private val resetButton by lazy { findViewById<TextView>(R.id.reset) }
    private val manager by lazy { CardStackLayoutManager(this, this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Injector.obtain(this)!!.inject(this)
        init()

        val gradientCardType = CardType.ZINC_CARD.cardColor as GradientCardColor

        resetButton.setOnClickListener { presenter.loadCharacters() }

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
        manager.setTranslationInterval(20.0f)
        manager.setScaleInterval(0.95f)
        manager.setSwipeThreshold(0.3f)
        manager.setMaxDegree(50.0f)
        manager.setDirections(Arrays.asList(Direction.Left, Direction.Top, Direction.Right))
        manager.setCanScrollHorizontal(true)
        manager.setCanScrollVertical(true)
        manager.setSwipeableMethod(SwipeableMethod.AutomaticAndManual)
        manager.setOverlayInterpolator(LinearInterpolator())
        //manager.setRewindAnimationSetting(rewindSetting)

        recycler.layoutManager = manager
        recycler.adapter = adapter
    }

    override fun showCharacters(characterList: List<Character>) {
        recycler.visibility = View.VISIBLE
        adapter.items = characterList
        adapter.notifyDataSetChanged()
    }

    override fun hideDeck() {
        recycler.visibility = View.GONE
    }

    override fun notifyLifes(lifesCounter: Int) {
        lifeCounter.text = lifesCounter.toString()
    }

    override fun notifyWrongAnswer(direction: String) {

        val customDialog = CustomDialog(this,R.drawable.game_over_1, "Game Over!!", "Has perdido tus vidas",
            object : CustomDialogClickListener {
                override fun onButtonClickListener() {
                    presenter.loadCharacters()
                }
            })

        customDialog.showDialog()
    }

    override fun onCardDragging(direction: Direction?, ratio: Float) {

    }

    override fun onCardSwiped(direction: Direction?) {
        presenter.onCardSwiped(direction?.name)
    }

    override fun onCardCanceled() {
    }

    override fun onCardAppeared(view: View?, position: Int) {
        presenter.onCurrentCard(position)
    }

    override fun onCardRewound() {
        Log.d("dev rick and morty", "vuelvo a la carta anterior")
    }

    override fun onCardDisappeared(view: View?, position: Int) {
        presenter.onCardDisappeared(position)
    }
}
