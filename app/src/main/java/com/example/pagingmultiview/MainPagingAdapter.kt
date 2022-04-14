package com.example.pagingmultiview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.pagingmultiview.databinding.ItemMainListBinding

class MainPagingAdapter : PagingDataAdapter<MainPagingModel, RecyclerView.ViewHolder>(DIFF_CALLBACK) {
    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<MainPagingModel>() {
            override fun areItemsTheSame(oldItem: MainPagingModel, newItem: MainPagingModel): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: MainPagingModel, newItem: MainPagingModel): Boolean {
                return oldItem == newItem
            }
        }
    }

    inner class MainListViewHolder(val binding: ItemMainListBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
        val binding: ItemMainListBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_main_list,
            parent,
            false
        )
        return MainListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is MainListViewHolder -> {
                holder.binding.apply {

                }
            }
            else -> {

            }
        }
    }

}