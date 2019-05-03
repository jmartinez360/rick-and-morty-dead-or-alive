package com.dev.rickandmortydeadoralive

import android.content.Context

object Injector {

    fun obtain(context: Context): AppGraph? {
        return App[context].injector
    }

    internal fun create(app: App): AppGraph {
        return DaggerAppComponent.builder().appModule(AppModule(app)).build()
    }
}