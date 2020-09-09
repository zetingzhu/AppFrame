package com.example.mypagingsamples.data

import android.util.Log
import androidx.paging.PagingSource
import com.example.mypagingsamples.MainActivity
import com.example.mypagingsamples.api.GitHubService
import com.example.mypagingsamples.api.GitHubService.Companion.IN_QUALIFIER
import com.example.mypagingsamples.model.GithubData

// GitHub page API is 1 based: https://developer.github.com/v3/#pagination
private const val GITHUB_STARTING_PAGE_INDEX = 1

class GithubPagingSource(private val service: GitHubService, private val query: String) : PagingSource<Int, GithubData>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, GithubData> {
        // page
        val position = params.key ?: GITHUB_STARTING_PAGE_INDEX
        // 查询条件
        val apiQuery = query + IN_QUALIFIER
        Log.d(MainActivity.TAG, "这个地方的查询条件：position:$position -query:$apiQuery")
        var loadResult: PagingSource.LoadResult.Page<Int, GithubData>? = null
        try {
            /**
             * 获取网络数据
             * service.getGithubAccount（）使用了suspend修饰符标记
             * 调用函数可能挂起协程，挂起函数挂起协程时，不会阻塞协程所在的线程
             */
            val response = service.getGithubAccount(apiQuery, position, params.loadSize)
            val repos = response.items
            // 请求成功，构造一个 LoadResult.Page 返回
            loadResult = LoadResult.Page(
                    data = repos,
                    prevKey = if (position == GITHUB_STARTING_PAGE_INDEX) null else position - 1,
                    nextKey = if (repos.isEmpty()) null else position + 1
            )
        } catch (e: Exception) {
            return LoadResult.Error(e)
        }
        return loadResult
    }
}