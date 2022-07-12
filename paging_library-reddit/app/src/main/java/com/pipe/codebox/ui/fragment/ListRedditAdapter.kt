package com.pipe.codebox.ui.fragment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.pipe.codebox.databinding.RecyclerRedditBinding
import com.pipe.codebox.domain.entity.PostEntity
import javax.inject.Inject

class ListRedditAdapter @Inject constructor() :
    PagingDataAdapter<PostEntity, ListRedditAdapter.ListViewHolder>(DiffUtils) {

    object DiffUtils : DiffUtil.ItemCallback<PostEntity>() {
        override fun areItemsTheSame(oldItem: PostEntity, newItem: PostEntity): Boolean =
            oldItem.title == newItem.title

        override fun areContentsTheSame(oldItem: PostEntity, newItem: PostEntity): Boolean =
            oldItem == newItem
    }

    private var onContainerClickListener: ((PostEntity) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ListViewHolder(
        RecyclerRedditBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    inner class ListViewHolder(private val binding: RecyclerRedditBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(post: PostEntity) = with(binding) {
            description.text = post.title
            stars.text = post.totalAwardsReceived.toString()
            comments.text = post.numComments
            cardReddit.setOnClickListener { onContainerClickListener?.let { it(post) } }
        }
    }

    fun setOnPostClickListener(listener: (PostEntity) -> Unit) {
        onContainerClickListener = listener
    }

}