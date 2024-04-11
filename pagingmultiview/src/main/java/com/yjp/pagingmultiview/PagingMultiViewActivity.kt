package com.yjp.pagingmultiview

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.yjp.pagingmultiview.databinding.ActivityPagingMultiViewBinding
import kotlinx.coroutines.launch

class PagingMultiViewActivity : AppCompatActivity() {
    private var _binding: ActivityPagingMultiViewBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MainViewModel by viewModels()
    private val mainPagingAdapter = MainPagingAdapter()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = DataBindingUtil.setContentView(this, R.layout.activity_paging_multi_view)
        initLayout()
        initData()
    }
    private fun initLayout() {
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = mainPagingAdapter
    }
    private fun initData() {
        lifecycleScope.launch {
            viewModel.getPagingList().collect {
                mainPagingAdapter.submitData(it)
            }
        }
    }
}