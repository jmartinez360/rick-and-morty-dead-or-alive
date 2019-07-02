package com.dev.rickandmortydeadoralive.ui.presenters

import com.dev.rickandmortydeadoralive.SchedulerInterface
import com.dev.rickandmortydeadoralive.models.Character
import com.dev.rickandmortydeadoralive.repository.RemoteCharactersRepository
import com.dev.rickandmortydeadoralive.ui.views.CharactersDeckView
import com.dev.rickandmortydeadoralive.ui.views.View
import com.dev.rickandmortydeadoralive.utils.RandomUtils
import io.reactivex.disposables.Disposable
import javax.inject.Inject

class CharactersDeckPresenter @Inject constructor (private val charaterRepository: RemoteCharactersRepository, private val schedulerProvider: SchedulerInterface): Presenter {

    companion object {
        private val LIST_SIZE = 10
        private val RIGHT_SWIPE = "Right"
        private val LEFT_SWIPE = "Left"
        private val TOP_SWIPE = "Top"
        private val UNKNOWN = "Unknown"
        private val MAX_LIFES = 3
    }

    lateinit var view: CharactersDeckView
    private var disposable: Disposable? = null
    private var charactersList : List<Character> = emptyList()
    private var correctSwipeDirection : String? = null
    private var lifeCount = MAX_LIFES

    override fun setView(view: View) {
        this.view = view as CharactersDeckView
    }

    fun loadCharacters() {
        lifeCount = MAX_LIFES
        view.notifyLifes(lifeCount)
        val ids = RandomUtils.getRandomNumberList(LIST_SIZE)
        disposable = charaterRepository.getCharacters(ids)
            .observeOn(schedulerProvider.ui())
            .subscribeOn(schedulerProvider.io())
            .subscribe {
                charactersList = it.shuffled()
                view.showCharacters(charactersList)
            }
    }

    fun onCurrentCard(position: Int) {
        val currentCard = charactersList[position]

        correctSwipeDirection = when(currentCard.status) {
            Character.ALIVE -> LEFT_SWIPE

            Character.DEAD -> RIGHT_SWIPE

            Character.UNKNOWN -> TOP_SWIPE

            else -> UNKNOWN
        }
    }

    fun onCardSwiped(direction: String?) {
        if (!direction.equals(correctSwipeDirection)) {
            lifeCount--
            view.notifyLifes(lifeCount)
            if (lifeCount == 0) {
                view.notifyWrongAnswer(direction!!)
            }
        }
    }

    fun onCardDisappeared(position: Int) {
        if (position + 1 == charactersList.size) {
            view.hideDeck()
        }
    }

}