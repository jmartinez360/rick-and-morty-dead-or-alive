package com.dev.rickandmortydeadoralive.ui.presenters

import android.util.Log
import com.dev.rickandmortydeadoralive.SchedulerInterface
import com.dev.rickandmortydeadoralive.repository.RemoteCharactersRepository
import com.dev.rickandmortydeadoralive.ui.views.CharactersDeckView
import com.dev.rickandmortydeadoralive.ui.views.View
import io.reactivex.disposables.Disposable
import javax.inject.Inject

class CharactersDeckPresenter @Inject constructor (private val charaterRepository: RemoteCharactersRepository, private val schedulerProvider: SchedulerInterface): Presenter {

    lateinit var view: CharactersDeckView
    private var disposable: Disposable? = null

    override fun setView(view: View) {
        this.view = view as CharactersDeckView
    }

    fun loadCharacters() {
        val ids: MutableList<Int> = mutableListOf(1, 23, 48);
        disposable = charaterRepository.getCharacters(ids)
            .observeOn(schedulerProvider.ui())
            .subscribeOn(schedulerProvider.io())
            .subscribe {
                view.showCharacters(it)
            }
    }
}