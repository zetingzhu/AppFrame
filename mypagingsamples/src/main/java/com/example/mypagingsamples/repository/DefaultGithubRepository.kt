package com.example.mypagingsamples.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.mypagingsamples.api.GitHubService
import com.example.mypagingsamples.data.GithubPagingSource
import com.example.mypagingsamples.model.GithubData
import kotlinx.coroutines.flow.Flow

class DefaultGithubRepository constructor(val gitHubApi: GitHubService) : GithubRepository {

    override fun getSearchResultStream(query: String): Flow<PagingData<GithubData>> {

        val pagingConfig = PagingConfig(
                // 每页显示的数据的大小
                pageSize = NETWORK_PAGE_SIZE,

                // 开启占位符
                enablePlaceholders = false

                // 预刷新的距离，距离最后一个 item 多远时加载数据
//        prefetchDistance = 3,

                /**
                 * 初始化加载数量，默认为 pageSize * 3
                 *
                 * internal const val DEFAULT_INITIAL_PAGE_MULTIPLIER = 3
                 * val initialLoadSize: Int = pageSize * DEFAULT_INITIAL_PAGE_MULTIPLIER
                 */
//        initialLoadSize = 60
        )

        return Pager(
                config = pagingConfig,
                pagingSourceFactory = { GithubPagingSource(gitHubApi, query) }
        ).flow
    }

    companion object {
        private const val NETWORK_PAGE_SIZE = 20
    }
}