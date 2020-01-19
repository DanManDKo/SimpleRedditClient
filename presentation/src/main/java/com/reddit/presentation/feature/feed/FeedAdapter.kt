package com.reddit.presentation.feature.feed

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.reddit.domain.model.Feed
import com.reddit.presentation.R
import com.reddit.presentation.databinding.ItemFeedBinding

/**
 * Created with Android Studio.
 * User: Danil Konovalenko
 * Date: 2020-01-19
 * Time: 14:36
 */
class FeedAdapter(private val vm: FeedViewModel) :
    ListAdapter<Feed, FeedAdapter.ItemViewHolder>(DIFF_CALLBACK) {

    companion object {
        private val DIFF_CALLBACK = object
            : DiffUtil.ItemCallback<Feed>() {
            override fun areItemsTheSame(old: Feed, new: Feed): Boolean {
                return old.id == new.id
            }

            override fun areContentsTheSame(old: Feed, new: Feed): Boolean {
                return old == new
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding: ItemFeedBinding = DataBindingUtil
            .inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_feed, parent, false
            )
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ItemViewHolder(private val binding: ItemFeedBinding) :
        RecyclerView.ViewHolder(binding.root) {

        private val item = FeedItemViewModel(binding.root.context)

        fun bind(feed: Feed) {
            item.bind(feed)
            binding.vm = vm
            binding.item = item
            binding.executePendingBindings()
        }
    }
}