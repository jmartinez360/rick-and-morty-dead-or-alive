package com.dev.rickandmortydeadoralive.ui.activities

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
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
import com.dev.rickandmortydeadoralive.utils.Constants
import com.dev.rickandmortydeadoralive.utils.CustomDialog
import com.dev.rickandmortydeadoralive.utils.CustomDialogClickListener
import com.dev.rickandmortydeadoralive.utils.PreCachingLayoutManager
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
        const val REQUEST_CODE = 2
        const val FILTER_DONE_RESULT_CODE = 3
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

        lifecycle.addObserver(viewModel)
        viewModel.characterListToPrint.observe(this, androidx.lifecycle.Observer { characters ->
            if (characters != null) {
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

        viewModel.errorLiveData.observe(this, Observer {
            it?.let { message ->
                Toast.makeText(this, message, Toast.LENGTH_LONG).show()
            }
        })

        loadCharacters()
    }

    private fun init() {
        lifeCounter.setOnClickListener { openFilter() }
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

    private fun loadCharacters() {
        val sharedPreferences = getSharedPreferences(Constants.FILTER_PREFERENCES, Context.MODE_PRIVATE)
        var filteredName: String?
        var filteredStatus: String?
        var filteredGender: String?


        sharedPreferences.apply {
            filteredName = getString(Constants.NAME_FILTER, null)
            filteredStatus = getString(Constants.STATUS_FILTER, null)
            filteredGender = getString(Constants.GENDER_FILTER, null)
        }

        viewModel.loadCharacters(
            filteredName = filteredName,
            filteredGender = filteredGender,
            filteredStatus = filteredStatus
        )
    }

    private fun openFilter() =
        startActivityForResult(Intent(this, FilterActivity::class.java), REQUEST_CODE)

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == FILTER_DONE_RESULT_CODE) {
            Log.d("rick", "estoy aqui con result code $resultCode y rquestCode $requestCode")
           // adapter.items = emptyList()
            loadCharacters()
        }
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
