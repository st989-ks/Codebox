package com.pipe.codebox.ui.fragment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.pipe.codebox.databinding.ProgressBarBinding

class ReposLoadStateAdapter() :
    LoadStateAdapter<ReposLoadStateAdapter.LoadViewHolder>() {

    override fun onBindViewHolder(holder: LoadViewHolder, loadState: LoadState) =
        holder.bind(loadState)

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState) = LoadViewHolder(
        ProgressBarBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    inner class LoadViewHolder(private val binding: ProgressBarBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(loadState: LoadState) = with(binding) {
            if (loadState is LoadState.Error) {
                errorMsg.text = loadState.error.localizedMessage
            }
            progressBar.isVisible = loadState is LoadState.Loading
            retryButton.isVisible = loadState is LoadState.Error
            errorMsg.isVisible = loadState is LoadState.Error
        }
    }
}