package com.dev.rickandmortydeadoralive.api

import com.dev.rickandmortydeadoralive.utils.Constants
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
class ApiModule {

    @Provides
    internal fun provideRetrofit(): Retrofit {
        return retrofit2.Retrofit.Builder()
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(Constants.RICK_AND_MORTY_BASE_URL)
            .build()
    }

    @Provides
    internal fun provideNewsService(retrofit: Retrofit): CharactersApiServiceInterface {
        return retrofit.create<CharactersApiServiceInterface>(CharactersApiServiceInterface::class.java)
    }
}