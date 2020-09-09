package com.example.mypagingsamples.model

data class GithubData(
        val id: Long,
        val name: String,
        val full_name: String,
        val description: String?,
        val html_url: String,
        val stargazers_count: Int,
        val forks_count: Int,
        val language: String?
)