package com.example.mypagingsamples.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.mypagingsamples.model.GithubData
import com.example.mypagingsamples.repository.GithubRepository
import kotlinx.coroutines.flow.Flow

class MainViewModel constructor(private val repository: GithubRepository) : ViewModel() {
    private var currentQueryValue: String? = null
    private var currentSearchResult: Flow<PagingData<GithubData>>? = null
    fun searchRepos(queryString: String): Flow<PagingData<GithubData>> {
        val lastResult = currentSearchResult
        if (queryString == currentQueryValue && lastResult != null) {
            return lastResult
        }
        currentQueryValue = queryString
        val newResult: Flow<PagingData<GithubData>> = repository.getSearchResultStream(queryString).cachedIn(viewModelScope)
        currentSearchResult = newResult
        return newResult
    }
}