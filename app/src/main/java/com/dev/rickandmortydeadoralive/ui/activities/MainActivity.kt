package com.dev.rickandmortydeadoralive.ui.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.dev.rickandmortydeadoralive.Injector
import com.dev.rickandmortydeadoralive.R
import com.dev.rickandmortydeadoralive.models.Character
import com.dev.rickandmortydeadoralive.ui.presenters.CharactersDeckPresenter
import com.dev.rickandmortydeadoralive.ui.views.CharactersDeckView
import javax.inject.Inject

class MainActivity : AppCompatActivity(), CharactersDeckView {

    @Inject
    lateinit var presenter: CharactersDeckPresenter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Injector.obtain(this)!!.inject(this)

        presenter.view = this
        presenter.loadCharacters()
    }

    override fun showCharacters(characterList: List<Character>) {
        Log.d("rick and morty", characterList.toString())

    }
}
