package com.example.catfacts.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.ExperimentalPagingApi
import com.example.catfacts.databinding.ActivityMainBinding
import com.example.catfacts.ui.adapter.PagingCatFactAdapter
import com.example.catfacts.ui.viewmodel.CatFactsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

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

        useRemoteMediator(adapter)

        setContentView(binding.root)
    }

    @OptIn(ExperimentalPagingApi::class)
    private fun useRemoteMediator(adapter: PagingCatFactAdapter){
        lifecycleScope.launch {
            viewModel.pagerWithRemoteMediator.collectLatest {
                adapter.submitData(it)
            }
        }
    }
}