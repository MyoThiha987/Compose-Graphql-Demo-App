package com.myothiha.composegraphql.di

import com.myothiha.composegraphql.data.SpacexShipRepositoryImpl
import com.myothiha.composegraphql.domain.SpacexShipsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

/**
 * @Author Liam
 * Created at 01/Sep/2024
 */

@Module
@InstallIn(ViewModelComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindSpacexShipsRepository(impl: SpacexShipRepositoryImpl): SpacexShipsRepository
}