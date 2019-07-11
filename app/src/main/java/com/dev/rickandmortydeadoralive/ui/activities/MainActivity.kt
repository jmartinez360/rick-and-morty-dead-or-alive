package com.dev.rickandmortydeadoralive.ui.activities

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.dev.rickandmortydeadoralive.Injector
import com.dev.rickandmortydeadoralive.R
import com.dev.rickandmortydeadoralive.models.Character
import com.dev.rickandmortydeadoralive.ui.adapters.CharacterCardsAdapter
import com.dev.rickandmortydeadoralive.ui.adapters.CustomScrollListener
import com.dev.rickandmortydeadoralive.ui.adapters.listeners.CardClickListener
import com.dev.rickandmortydeadoralive.ui.adapters.listeners.ItemTouchHelperCallback
import com.dev.rickandmortydeadoralive.ui.adapters.listeners.OnStartDragListener
import com.dev.rickandmortydeadoralive.ui.presenters.CharactersViewModel
import com.dev.rickandmortydeadoralive.ui.views.CharactersDeckView
import com.dev.rickandmortydeadoralive.utils.CustomDialog
import com.dev.rickandmortydeadoralive.utils.CustomDialogClickListener
import com.dev.rickandmortydeadoralive.utils.PreCachingLayoutManager
import com.dev.rickandmortydeadoralive.utils.cardColors.CardType
import com.dev.rickandmortydeadoralive.utils.cardColors.GradientCardColor
import com.yuyakaido.android.cardstackview.CardStackListener
import com.yuyakaido.android.cardstackview.Direction
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject
import android.content.DialogInterface

import androidx.appcompat.app.AlertDialog


class MainActivity : AppCompatActivity(), CharactersDeckView, CardStackListener,
    CardClickListener, OnStartDragListener {

    companion object {
        private val SPAN_COUNT = 2
    }

    @Inject
    lateinit var viewModel: CharactersViewModel

    lateinit var characterList: LiveData<List<Character>>

    private var isLoading = false
    private var isLastPage = false


    private val adapter by lazy { CharacterCardsAdapter(clickListener = this, dragStartListener = this) }
    private val recycler by lazy { findViewById<RecyclerView>(R.id.characterRecycler) }
    private val resetButton by lazy { findViewById<TextView>(R.id.reset) }
    private val gridLayoutManager by lazy { PreCachingLayoutManager(this) }
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
                Log.d("pagina", "recibo para pintar")

                showCharacters(characters)
            }
        })

        viewModel.isLastPage.observe(this, Observer {
            if (it != null) {
                isLastPage = it
                Log.d("pagina", "es ultima pagina? $isLastPage")

            }
        })

        viewModel.isLoading.observe(this, Observer {
            if (it != null) {
                isLoading = it
                Log.d("pagina", "está cargando? $isLoading")

            }
        })
        viewModel.loadCharacters()
    }

    fun init() {
        lifeCounter.setOnClickListener { showFilterDialog() }
        recycler.layoutManager = gridLayoutManager
        recycler.setHasFixedSize(true)
        recycler.adapter = adapter
        itemTouchHelper.attachToRecyclerView(recycler)


        recycler.addOnScrollListener(object : CustomScrollListener(gridLayoutManager) {
            override fun isLastPage(): Boolean {
                return isLastPage
            }

            override fun isLoading(): Boolean {
                return isLoading
            }

            override fun loadMoreItems() {
                viewModel.nextPage()
            }

        })
    }

    override fun showCharacters(characterList: List<Character>) {
        recycler.visibility = View.VISIBLE
        adapter.items = characterList
        //adapter.notifyDataSetChanged()
    }

    private fun showFilterDialog() {
        val builder = AlertDialog.Builder(this)
        val filters = arrayOf("dead", "alive", "male", "female")
        builder.setTitle("Elige filtro")
            .setItems(filters, DialogInterface.OnClickListener { dialog, which ->
                viewModel.filterInLocal(filter = filters[which])
            })
        builder.create().show()
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
