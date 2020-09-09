package com.example.mypagingsamples.model

data class GithubSearchResponse(
        val total_count: Int = 0,
        val items: List<GithubData> = emptyList(),
        val nextPage: Int? = null
)