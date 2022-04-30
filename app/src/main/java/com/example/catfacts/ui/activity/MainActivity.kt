package com.example.catfacts.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.catfacts.databinding.ActivityMainBinding
import com.example.catfacts.ui.adapter.PagingCatFactAdapter
import com.example.catfacts.ui.viewmodel.CatFactsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

private const val TAG = "MAIN_ACTIVITY"

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel by viewModels<CatFactsViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        binding.lifecycleOwner = this
        binding.vm = viewModel

        val adapter = PagingCatFactAdapter()
        binding.recyclerView.adapter = adapter

        usePagingSource(adapter)

        setContentView(binding.root)
    }

    private fun usePagingSource(adapter: PagingCatFactAdapter){
        binding.buttonRefresh.setOnClickListener {
            adapter.refresh()
        }

        lifecycleScope.launch {
            viewModel.pagerNoRemoteMediator.collectLatest {
                adapter.submitData(it)
            }
        }

        lifecycleScope.launch {
            viewModel.currentPage.collect {
                binding.textViewCurrentPage.text = it
            }
        }
    }
}