package com.example.mypagingsamples.adapter

import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.mypagingsamples.model.GithubData

class GitHubAdapter : PagingDataAdapter<GithubData, RecyclerView.ViewHolder>(REPO_COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return RepoViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val repoItem = getItem(position)
        if (repoItem != null) {
            (holder as RepoViewHolder).bind(repoItem)
        }
    }

    companion object {
        private val REPO_COMPARATOR = object : DiffUtil.ItemCallback<GithubData>() {
            override fun areItemsTheSame(oldItem: GithubData, newItem: GithubData): Boolean =
                    oldItem.full_name == newItem.full_name

            override fun areContentsTheSame(oldItem: GithubData, newItem: GithubData): Boolean =
                    oldItem == newItem
        }
    }
}