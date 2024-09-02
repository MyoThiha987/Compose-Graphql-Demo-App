package com.myothiha.composegraphql.domain

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow

/**
 * @Author Liam
 * Created at 01/Sep/2024
 */
interface SpacexShipsRepository {
    suspend fun getShips(): Flow<PagingData<Ship>>
}