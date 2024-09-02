package com.myothiha.composegraphql.data

import androidx.paging.PagingSource
import androidx.paging.PagingState

/**
 * @Author Liam
 * Created at 01/Sep/2024
 */

open class SpaceXPagingSource<T : Any>(private val pagingData: suspend (page: Int, pageSize: Int) -> List<T>) :
    PagingSource<Int, T>() {
    override fun getRefreshKey(state: PagingState<Int, T>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, T> =
        (params.key ?: 1).let { _page ->
            runCatching {
                pagingData(_page, params.loadSize).run {
                    val data = this
                    LoadResult.Page(
                        data = data,
                        /* no previous pagination int as page */
                        prevKey = _page.takeIf { it > 1 }?.dec(),
                        /* no pagination if no results found else next page as +1 */
                        nextKey = _page.takeIf { data.size >= params.loadSize }?.inc()
                    )
                }
            }.getOrElse {
                LoadResult.Error(it)
            }
        }
}