package com.example.giphy_update.ui.movie

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.example.giphy_update.data.model.MovieData
import com.example.giphy_update.databinding.ItemMovieBinding
import com.example.giphy_update.ui.base.BaseRecyclerAdapter
import com.example.giphy_update.ui.base.BaseViewHolder
import com.example.giphy_update.ui.webview.WebViewFragment

class MovieAdapter : BaseRecyclerAdapter<MovieData, MovieAdapter.MovieHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = MovieHolder(
        ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    inner class MovieHolder(private val binding: ItemMovieBinding) :
        BaseViewHolder<MovieData>(binding.root) {
        private lateinit var item: MovieData

        init {
            binding.setOnClick {
                Intent(it.context, WebViewFragment::class.java).apply {
                    putExtra("movie", item.link)
                }.run { it.context.startActivity(this) }
            }
        }

        override fun bind(item: MovieData) {
            this.item = item
            with(binding) {
                items = item
                executePendingBindings()

            }
        }

    }

}

private class DiffCallback : DiffUtil.ItemCallback<MovieData>() {
    // 두 객체가 같은 항목인지.
    override fun areItemsTheSame(oldItem: MovieData, newItem: MovieData): Boolean {
        return oldItem == newItem

    }

    // 두 항목의 데이터가 같은지.
    // areItemsTheSame 가 true 일떄만 호출.
    override fun areContentsTheSame(oldItem: MovieData, newItem: MovieData): Boolean {
        return oldItem == newItem


    }

}