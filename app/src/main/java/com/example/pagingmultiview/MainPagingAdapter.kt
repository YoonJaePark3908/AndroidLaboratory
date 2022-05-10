package com.example.pagingmultiview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.pagingmultiview.databinding.ItemMainListBinding
import com.example.pagingmultiview.model.RespTestModel

class MainPagingAdapter: PagingDataAdapter<RespTestModel.MsgBody, RecyclerView.ViewHolder>(DIFF_CALLBACK) {
    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<RespTestModel.MsgBody>() {
            override fun areItemsTheSame(oldItem: RespTestModel.MsgBody, newItem: RespTestModel.MsgBody): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: RespTestModel.MsgBody, newItem: RespTestModel.MsgBody): Boolean {
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
                    data = getItem(position)
                }
            }
            else -> {

            }
        }
    }
}