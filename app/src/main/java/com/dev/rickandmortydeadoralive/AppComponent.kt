package com.dev.rickandmortydeadoralive

import com.dev.rickandmortydeadoralive.api.ApiModule
import com.dev.rickandmortydeadoralive.repository.DataModule
import dagger.Component
import javax.inject.Singleton

@Component(
    modules = arrayOf(
        AppModule::class,
        ApiModule::class,
        DataModule::class)
)

@Singleton
interface AppComponent : AppGraph {
}