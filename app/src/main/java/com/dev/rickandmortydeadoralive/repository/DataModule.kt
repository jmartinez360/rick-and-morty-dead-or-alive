package com.dev.rickandmortydeadoralive.repository

import com.dev.rickandmortydeadoralive.api.CharactersApiServiceInterface
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DataModule {

    @Provides
    @Singleton
    internal fun provideRemoteCharactersDataSource(
        charactersService:
        CharactersApiServiceInterface
    ): RemoteCharactersDataSource {
        return RemoteCharactersDataSource(charactersService)
    }

    @Provides
    @Singleton
    internal fun provideRemoteCharactersRepository(
        remoteCharactersDataSource:
        RemoteCharactersDataSource
    ): RemoteCharactersRepository {
        return RemoteCharactersRepository(remoteCharactersDataSource)
    }

}