package com.dev.rickandmortydeadoralive.ui.adapters.listeners

interface ItemTouchAdapter {

    fun onItemMove(fromPosition: Int, toPosition: Int) : Boolean

}