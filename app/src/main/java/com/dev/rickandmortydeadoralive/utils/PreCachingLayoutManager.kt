package com.dev.rickandmortydeadoralive.utils

import android.content.Context
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class PreCachingLayoutManager : GridLayoutManager {
    private val defaultExtraLayoutSpace = 600
    private var extraLayoutSpace = -1
    private var context: Context? = null

    constructor(context: Context?) : super(context, 2) {
        this.context = context
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