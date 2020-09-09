package com.example.mypagingsamples.vm

import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.mypagingsamples.model.GithubData
import com.example.mypagingsamples.repository.GithubRepository
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow

class MainViewModel2 constructor(private val repository: GithubRepository) : ViewModel() {

    private val viewModelJob = SupervisorJob()

    val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)
    val dispatcher = Dispatchers.IO

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }


    var gitHubLiveData: LiveData<PagingData<GithubData>>? = null

    fun searchRepos(queryString: String) {
        gitHubLiveData = repository.getSearchResultStream(queryString).asLiveData()
    }

    var currentQueryValue: String? = null
    val searchResult: MutableLiveData<PagingData<GithubData>> by lazy {
        MutableLiveData<PagingData<GithubData>>()
    }
    var currentSearchResult: PagingData<GithubData>? = null

//    fun searchRepos(queryString: String) {
//        uiScope.launch {
//            searchResult.value = withContext(Dispatchers.IO) {
//                val lastResult = currentSearchResult
//                if (queryString == currentQueryValue && lastResult != null) {
//                    lastResult
//                } else {
//                    currentQueryValue = queryString
//                    val newResult: Flow<PagingData<GithubData>> = repository.getSearchResultStream(queryString).cachedIn(viewModelScope)
//                    currentSearchResult = newResult.asLiveData().value
//                    currentSearchResult
//                }
//            }
//        }
//    }

}