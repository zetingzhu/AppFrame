package com.example.mypagingsamples

import android.os.Bundle
import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.*
import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingData
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.mypagingsamples.adapter.GitHubAdapter
import com.example.mypagingsamples.api.GitHubService
import com.example.mypagingsamples.model.GithubData
import com.example.mypagingsamples.repository.DefaultGithubRepository
import com.example.mypagingsamples.repository.GithubRepository
import com.example.mypagingsamples.vm.MainViewModel
import com.example.mypagingsamples.vm.MainViewModel2
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest

class MainActivity : AppCompatActivity() {
    //Repository Flow

    companion object {
        val TAG = MainActivity.javaClass.name
    }

    private var searchJob: Job? = null

    private val gitHubAdapter = GitHubAdapter()


    lateinit var gitHubService: GitHubService
    lateinit var githubRepository: DefaultGithubRepository

    lateinit var mainViewModel: MainViewModel
    lateinit var mainViewModel2: MainViewModel2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        gitHubService = GitHubService.create()
        githubRepository = DefaultGithubRepository(gitHubService)

        mainViewModel = ViewModelProviders.of(this@MainActivity, object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return modelClass.getConstructor(GithubRepository::class.java).newInstance(githubRepository)
            }
        }).get(MainViewModel::class.java)

        mainViewModel2 = ViewModelProviders.of(this@MainActivity, object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return modelClass.getConstructor(GithubRepository::class.java).newInstance(githubRepository)
            }
        }).get(MainViewModel2::class.java)

        list.apply {
            val decoration = DividerItemDecoration(this@MainActivity, DividerItemDecoration.VERTICAL)
            addItemDecoration(decoration)
            adapter = gitHubAdapter
        }

        val query = "Android"
        search(query)
        initSearch(query)


    }

    private fun search(query: String) {

        // Make sure we cancel the previous job before creating a new one
        searchJob?.cancel()
        searchJob = lifecycleScope.launch {
            mainViewModel.searchRepos(query).collectLatest {
                gitHubAdapter.submitData(it)
            }
        }

//        mainViewModel2.searchResult.observe(this@MainActivity, object : Observer<PagingData<GithubData>> {
//            override fun onChanged(t: PagingData<GithubData> ) {
//                gitHubAdapter.submitData(lifecycle ,t )
//            }
//        })


        //Android 中常用的GlobalScope.launch(Dispatchers.Main){}
        GlobalScope.launch(Dispatchers.Main) { // 在 UI 线程创建一个新协程
        }

    }

    private fun initSearch(query: String) {
        search_repo.setText(query)

        search_repo.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_GO) {
                updateRepoListFromInput()
                true
            } else {
                false
            }
        }
        search_repo.setOnKeyListener { _, keyCode, event ->
            if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                updateRepoListFromInput()
                true
            } else {
                false
            }
        }

        lifecycleScope.launch {
            @OptIn(ExperimentalPagingApi::class)
            gitHubAdapter.dataRefreshFlow.collect {
                list.scrollToPosition(0)
            }
        }
    }


    private fun updateRepoListFromInput() {
        search_repo.text.trim().let {
            if (it.isNotEmpty()) {
                search(it.toString())
            }
        }
    }

}
