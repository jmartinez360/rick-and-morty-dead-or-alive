package com.dev.rickandmortydeadoralive

import dagger.Module
import dagger.Provides

@Module
class AppModule (val application : App) {

    @Provides
    fun provideApp() = application

    @Provides
    fun provideContext() = application
}