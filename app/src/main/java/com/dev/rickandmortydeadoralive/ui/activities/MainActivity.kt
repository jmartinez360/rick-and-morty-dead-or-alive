package com.dev.rickandmortydeadoralive.ui.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
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
import com.dev.rickandmortydeadoralive.ui.adapters.listeners.CardListener
import com.dev.rickandmortydeadoralive.ui.adapters.listeners.ItemTouchHelperCallback
import com.dev.rickandmortydeadoralive.ui.presenters.CharactersViewModel
import com.dev.rickandmortydeadoralive.ui.views.CharactersDeckView
import com.dev.rickandmortydeadoralive.utils.Constants
import com.dev.rickandmortydeadoralive.utils.CustomDialog
import com.dev.rickandmortydeadoralive.utils.CustomDialogClickListener
import com.dev.rickandmortydeadoralive.utils.PreCachingLayoutManager
import com.dev.rickandmortydeadoralive.utils.cardColors.CardType
import com.dev.rickandmortydeadoralive.utils.cardColors.GradientCardColor
import com.dev.rickandmortydeadoralive.utils.filterTypes.FilterType
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject


class MainActivity : AppCompatActivity(), CharactersDeckView, CardListener {

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


    private val adapter by lazy { CharacterCardsAdapter(dragStartListener = this) }
    private val recycler by lazy { findViewById<RecyclerView>(R.id.characterRecycler) }
    private val gridLayoutManager by lazy { PreCachingLayoutManager(this) }
    private val itemTouchHelper by lazy { ItemTouchHelper(ItemTouchHelperCallback(adapter)) }
    private val sharedPreferences by lazy { getSharedPreferences(Constants.FILTER_PREFERENCES, Context.MODE_PRIVATE) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Injector.obtain(this)!!.inject(this)
        init()

        val gradientCardType = CardType.ZINC_CARD.cardColor as GradientCardColor

        lifecycle.addObserver(viewModel)
        viewModel.characterListToPrint.observe(this, androidx.lifecycle.Observer { characters ->
            if (characters != null) {
                showCharacters(characters)
            }
        })

        viewModel.isLastPage.observe(this, Observer {
            if (it != null) {
                isLastPage = it
            }
        })

        viewModel.isLoading.observe(this, Observer {
            if (it != null) {
                isLoading = it
                progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
            }
        })

        viewModel.errorLiveData.observe(this, Observer {
            it?.let { message ->
                Toast.makeText(this, message, Toast.LENGTH_LONG).show()
                progressBar.visibility = View.GONE
            }
        })

        viewModel.filtersLiveData.observe(this, Observer { filtersMap ->
            setupFiltersInfo(filtersMap)
        })

        loadCharacters()
    }

    private fun setupFiltersInfo(filtersMap: Map<String, String>) {
        if (filtersMap.isNotEmpty()) {
            genderFilterChip.apply {
                text = filtersMap[Character.GENDER_FILTER]
                visibility = if (text.isNullOrEmpty()) View.GONE else View.VISIBLE
                setOnCloseIconClickListener { removeFilter(Constants.GENDER_FILTER) }
            }

            nameFilterChip.apply {
                text = filtersMap[Character.NAME_FILTER]
                visibility = if (text.isNullOrEmpty()) View.GONE else View.VISIBLE
                setOnCloseIconClickListener { removeFilter(Constants.NAME_FILTER) }

            }

            statusFilterChip.apply {
                text = filtersMap[Character.STATUS_FILTER]
                visibility = if (text.isNullOrEmpty()) View.GONE else View.VISIBLE
                setOnCloseIconClickListener { removeFilter(Constants.STATUS_FILTER) }
            }

        } else {
            genderFilterChip.visibility = View.GONE
            statusFilterChip.visibility = View.GONE
            nameFilterChip.visibility = View.GONE
        }
    }

    private fun removeFilter(filterKey: String) {
        sharedPreferences.edit().remove(filterKey).apply()
        loadCharacters()
    }

    private fun init() {
        floatingActionButton.setOnClickListener { openFilter() }
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
        var filteredName: String?
        var filteredStatus: String?
        var filteredGender: String?


        sharedPreferences.apply {
            filteredName = getString(Constants.NAME_FILTER, "")
            filteredStatus = getString(Constants.STATUS_FILTER, FilterType.WITHOUT_FILTERING)
            filteredGender = getString(Constants.GENDER_FILTER, FilterType.WITHOUT_FILTERING)
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
            loadCharacters()
        }
    }

    override fun showCharacters(characterList: List<Character>) {
        recycler.visibility = View.VISIBLE
        adapter.items = characterList
    }

    override fun hideDeck() {
        recycler.visibility = View.GONE
    }

    override fun notifyLifes(lifesCounter: Int) {
       /* no-op */
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

    override fun onCharacterClickListener(character: Character) {
        startActivity(CharacterDetailActivity.getIntent(character, context = this))
    }

    override fun onStartDrag(viewHolder: RecyclerView.ViewHolder) {
        itemTouchHelper.startDrag(viewHolder)
    }
}
