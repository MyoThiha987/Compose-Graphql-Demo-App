package com.myothiha.composegraphql.di

import com.apollographql.apollo.ApolloClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

/**
 * @Author Liam
 * Created at 01/Sep/2024
 */


@Module
@InstallIn(SingletonComponent::class)
object NetworkServiceModule {

    @Provides
    fun provideApolloClient(): ApolloClient {
        return ApolloClient.Builder()
            .serverUrl("https://spacex-production.up.railway.app")
            .build()
    }
}