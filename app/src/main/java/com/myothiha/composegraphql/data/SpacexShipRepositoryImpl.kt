package com.myothiha.composegraphql.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Optional
import com.myothiha.composegraphql.Query
import com.myothiha.composegraphql.domain.Ship
import com.myothiha.composegraphql.domain.SpacexShipsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * @Author Liam
 * Created at 01/Sep/2024
 */
const val PAGE_SIZE = 10
const val offset = 1

class SpacexShipRepositoryImpl @Inject constructor(
    private val client: ApolloClient
) : SpacexShipsRepository {
    var offset = 1
    override suspend fun getShips(): Flow<PagingData<Ship>> {
        return Pager(config = PagingConfig(
            pageSize = PAGE_SIZE,
            initialLoadSize = PAGE_SIZE,
            enablePlaceholders = false
        ), pagingSourceFactory = {
            SpaceXPagingSource { page, pageSize ->
                val a = client.query(
                    Query(
                        limit = Optional.present(PAGE_SIZE),
                        offset = Optional.present(offset)
                    )
                ).execute().data?.ships?.map {
                    Ship(
                        active = it?.active ?: false,
                        image = it?.image.orEmpty(),
                        id = it?.id.orEmpty(),
                        name = it?.name.orEmpty(),
                        model = it?.model.orEmpty()
                    )
                } ?: listOf()
                offset += PAGE_SIZE
                a
            }
        }).flow
    }
}