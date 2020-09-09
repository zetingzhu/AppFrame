package com.example.mypagingsamples.repository

import androidx.paging.PagingData
import com.example.mypagingsamples.model.GithubData
import kotlinx.coroutines.flow.Flow

interface GithubRepository {

    fun getSearchResultStream(query: String): Flow<PagingData<GithubData>>

}