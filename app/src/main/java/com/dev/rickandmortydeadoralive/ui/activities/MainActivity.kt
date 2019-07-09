package com.dev.rickandmortydeadoralive.ui.activities

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.dev.rickandmortydeadoralive.Injector
import com.dev.rickandmortydeadoralive.R
import com.dev.rickandmortydeadoralive.models.Character
import com.dev.rickandmortydeadoralive.ui.adapters.listeners.CardClickListener
import com.dev.rickandmortydeadoralive.ui.adapters.CharacterCardsAdapter
import com.dev.rickandmortydeadoralive.ui.adapters.listeners.ItemTouchHelperCallback
import com.dev.rickandmortydeadoralive.ui.adapters.listeners.OnStartDragListener
import com.dev.rickandmortydeadoralive.ui.presenters.CharactersViewModel
import com.dev.rickandmortydeadoralive.ui.views.CharactersDeckView
import com.dev.rickandmortydeadoralive.utils.CustomDialog
import com.dev.rickandmortydeadoralive.utils.CustomDialogClickListener
import com.dev.rickandmortydeadoralive.utils.cardColors.CardType
import com.dev.rickandmortydeadoralive.utils.cardColors.GradientCardColor
import com.yuyakaido.android.cardstackview.CardStackListener
import com.yuyakaido.android.cardstackview.Direction
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject


class MainActivity : AppCompatActivity(), CharactersDeckView, CardStackListener,
    CardClickListener, OnStartDragListener {

    companion object {
        private val SPAN_COUNT = 2
    }

    @Inject
    lateinit var viewModel: CharactersViewModel

    lateinit var characterList: LiveData<List<Character>>

    private val adapter by lazy { CharacterCardsAdapter(clickListener = this, dragStartListener = this) }
    private val recycler by lazy { findViewById<RecyclerView>(R.id.characterRecycler) }
    private val resetButton by lazy { findViewById<TextView>(R.id.reset) }
    private val gridLayoutManager by lazy { GridLayoutManager(this,   SPAN_COUNT) }
    private val itemTouchHelper by lazy { ItemTouchHelper(ItemTouchHelperCallback(adapter)) }


    /*private val viewModel: CharactersViewModel by lazy {
        ViewModelProviders.of(this).get(CharactersViewModel::class.java)
    }*/

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Injector.obtain(this)!!.inject(this)
        init()

        val gradientCardType = CardType.ZINC_CARD.cardColor as GradientCardColor

        resetButton.setOnClickListener { viewModel.loadCharacters() }
        lifecycle.addObserver(viewModel)


        viewModel.characterListToPrint.observe(this, androidx.lifecycle.Observer { characters ->
            if (characters != null && characters.isNotEmpty()) {
                showCharacters(characters)
            }
        })

        viewModel.loadCharacters()






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
        lifeCounter.setOnClickListener { viewModel.filterInLocal("ALIVE") }
        recycler.layoutManager = gridLayoutManager
        recycler.adapter = adapter
        itemTouchHelper.attachToRecyclerView(recycler)
    }

    override fun showCharacters(characterList: List<Character>) {
        recycler.visibility = View.VISIBLE
        adapter.items = characterList
        //adapter.notifyDataSetChanged()
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
                    //presenter.loadCharacters()
                    viewModel.restart()
                }
            })

        customDialog.showDialog()
    }

    override fun onCardDragging(direction: Direction?, ratio: Float) {
        /* no-op */
    }

    override fun onCardSwiped(direction: Direction?) {
    }

    override fun onCardCanceled() {
        /* no-op */
    }

    override fun onCardAppeared(view: View?, position: Int) {
        //presenter.onCurrentCard(position)
    }

    override fun onCardRewound() {
        /* no-op */
    }

    override fun onCardDisappeared(view: View?, position: Int) {
    }

    override fun onDeadClickListener(item: Character) {

    }

    override fun onAliveClickListener(item: Character) {

    }

    override fun onUnknownClickListener(item: Character) {

    }

    override fun onStartDrag(viewHolder: RecyclerView.ViewHolder) {
        itemTouchHelper.startDrag(viewHolder)
    }
}
