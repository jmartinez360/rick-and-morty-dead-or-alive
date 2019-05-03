package com.dev.rickandmortydeadoralive

import com.dev.rickandmortydeadoralive.ui.activities.MainActivity

interface AppGraph {

    fun inject(app: App)

    fun inject(mainActivity: MainActivity)

}