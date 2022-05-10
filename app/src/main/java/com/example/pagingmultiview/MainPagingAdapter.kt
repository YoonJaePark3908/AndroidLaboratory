package com.example.pagingmultiview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.pagingmultiview.databinding.ItemTouristBinding
import com.example.pagingmultiview.model.RespDaejeonTouristModel

class MainPagingAdapter: PagingDataAdapter<RespDaejeonTouristModel.MsgBody, RecyclerView.ViewHolder>(DIFF_CALLBACK) {
    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<RespDaejeonTouristModel.MsgBody>() {
            override fun areItemsTheSame(oldItem: RespDaejeonTouristModel.MsgBody, newItem: RespDaejeonTouristModel.MsgBody): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: RespDaejeonTouristModel.MsgBody, newItem: RespDaejeonTouristModel.MsgBody): Boolean {
                return oldItem == newItem
            }
        }
    }

    inner class MainListViewHolder(val binding: ItemTouristBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
        val binding: ItemTouristBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_tourist,
            parent,
            false
        )
        return MainListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is MainListViewHolder -> {
                holder.binding.apply {
                    data = getItem(position)
                }
            }
            else -> {

            }
        }
    }
}