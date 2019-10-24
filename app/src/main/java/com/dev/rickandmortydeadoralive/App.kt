package com.dev.rickandmortydeadoralive

import android.app.Application
import android.content.Context
import com.facebook.drawee.backends.pipeline.Fresco

class App : Application() {

    companion object {

        operator fun get(context: Context): App {
            return context.applicationContext as App
        }
    }

    internal var injector: AppGraph? = null

    override fun onCreate() {
        super.onCreate()

        // Dagger
        injector = Injector.create(this)
        injector!!.inject(this)
        Fresco.initialize(this)

    }
}