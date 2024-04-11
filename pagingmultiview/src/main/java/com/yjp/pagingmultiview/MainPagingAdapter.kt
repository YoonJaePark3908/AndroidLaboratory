package com.yjp.pagingmultiview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.yjp.pagingmultiview.model.MainPagingModel
import com.yjp.pagingmultiview.databinding.ItemRestaurantBinding
import com.yjp.pagingmultiview.databinding.ItemTouristBinding

class MainPagingAdapter: PagingDataAdapter<MainPagingModel, RecyclerView.ViewHolder>(DIFF_CALLBACK) {
    companion object {
        const val VIEW_TYPE_TOURIST = 1
        const val VIEW_TYPE_RESTAURANT = 2
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<MainPagingModel>() {
            override fun areItemsTheSame(oldItem: MainPagingModel, newItem: MainPagingModel): Boolean {
                return if (oldItem is MainPagingModel.TouristModel && newItem is MainPagingModel.TouristModel) {
                    oldItem.id == newItem.id
                } else if(oldItem is MainPagingModel.RestaurantModel && newItem is MainPagingModel.RestaurantModel) {
                    oldItem.idx == newItem.idx
                } else {
                    false
                }
            }

            override fun areContentsTheSame(oldItem: MainPagingModel, newItem: MainPagingModel): Boolean {
                return oldItem == newItem
            }
        }
    }

    inner class TouristViewHolder(val binding: ItemTouristBinding): RecyclerView.ViewHolder(binding.root)
    inner class RestaurantViewHolder(val binding: ItemRestaurantBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
        return if (viewType == VIEW_TYPE_TOURIST) {
            val binding: ItemTouristBinding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_tourist,
                parent,
                false
            )
            TouristViewHolder(binding)
        } else {
            val binding: ItemRestaurantBinding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_restaurant,
                parent,
                false
            )
            RestaurantViewHolder(binding)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is TouristViewHolder -> {
                holder.binding.apply {
                    val tourist = getItem(position)
                    if (tourist is MainPagingModel.TouristModel) {
                        data = tourist
                    }

                }
            }
            is RestaurantViewHolder -> {
                holder.binding.apply {
                    val restaurant = getItem(position)
                    if (restaurant is MainPagingModel.RestaurantModel) {
                        data = restaurant
                    }
                }
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        getItem(position)?.let {
            return if (it is MainPagingModel.TouristModel) VIEW_TYPE_TOURIST
            else VIEW_TYPE_RESTAURANT
        }
        return 0
    }
}