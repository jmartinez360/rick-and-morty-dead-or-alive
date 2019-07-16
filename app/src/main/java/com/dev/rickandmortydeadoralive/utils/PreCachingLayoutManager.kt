package com.dev.rickandmortydeadoralive.utils

import android.content.Context
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class PreCachingLayoutManager(context: Context?) : GridLayoutManager(context, 2) {
    private val defaultExtraLayoutSpace = 600
    private var extraLayoutSpace = -1

    init {
        extraLayoutSpace = defaultExtraLayoutSpace
    }

    fun setExtraLayoutSpace(extraLayoutSpace: Int) {
        this.extraLayoutSpace = extraLayoutSpace
    }
    override fun getExtraLayoutSpace(state: RecyclerView.State): Int {
        return if (extraLayoutSpace > 0) {
            extraLayoutSpace
        } else defaultExtraLayoutSpace
    }
}