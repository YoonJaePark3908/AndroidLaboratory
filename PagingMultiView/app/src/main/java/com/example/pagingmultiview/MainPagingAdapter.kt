package com.example.pagingmultiview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.pagingmultiview.databinding.ItemRestaurantBinding
import com.example.pagingmultiview.databinding.ItemTouristBinding
import com.example.pagingmultiview.model.MainPagingModel

class MainPagingAdapter: PagingDataAdapter<MainPagingModel, RecyclerView.ViewHolder>(DIFF_CALLBACK) {
    companion object {
        const val VIEW_TYPE_TOURIST = 1
        const val VIEW_TYPE_RESTAURANT = 2
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<MainPagingModel>() {
            override fun areItemsTheSame(oldItem: MainPagingModel, newItem: MainPagingModel): Boolean {
                return oldItem.id == newItem.id
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
                    getItem(position)?.let { mainPagingModel ->
                        data = mainPagingModel.tourist.apply {
                            addr1 = "${this.id}번째 주소명"
                            name = "${this.id}번째 여행지 이름"

                        }
                    }
                }
            }
            is RestaurantViewHolder -> {
                holder.binding.apply {
                    getItem(position)?.let { mainPagingModel ->
                        data = mainPagingModel.restaurant.apply {
                            name = "${this.idx}번째 음식점 이름"
                            contents1 = "${this.idx}번째 음식점 소개 내용"
                            topMenu = "${this.idx}번째 음식점 인기 메뉴"
                        }
                    }
                    data = getItem(position)?.restaurant
                }
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        getItem(position)?.let {
            return if (it.type == MainPagingModel.Type.Tourist) VIEW_TYPE_TOURIST
            else VIEW_TYPE_RESTAURANT
        }
        return 0
    }
}