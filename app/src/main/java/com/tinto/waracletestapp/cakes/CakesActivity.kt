package com.tinto.waracletestapp.cakes

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.tinto.waracletestapp.Extensions.isNetWorkConnected
import com.tinto.waracletestapp.R
import com.tinto.waracletestapp.adapter.CakesAdapter
import com.tinto.waracletestapp.databinding.ActivityCakesBinding
import com.tinto.waracletestapp.listeners.CakeItemClick
import com.tinto.waracletestapp.model.CakeDataModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class CakesActivity : AppCompatActivity(), CakeItemClick {

    private lateinit var binding: ActivityCakesBinding
    private val viewModel: CakesViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCakesBinding.inflate(layoutInflater)
        binding.cakeViewModel = viewModel
        setContentView(binding.root)
        initialSetup()
    }

    /* setting the observers and making the network call to get the data */
    private fun initialSetup() {
        if (isNetWorkConnected()) {
            viewModel.getCakesData()
        } else {
            viewModel.isLoading.postValue(false)
            viewModel.networkError.postValue(getString(R.string.no_internet))
        }
        viewModel.isLoading.observe(this) {
            populateData()
            binding.isLoading = it
        }

        viewModel.networkError.observe(this) {
            showDescriptionDialog(it)
        }

        binding.swipeRefreshLayout.setOnRefreshListener {
            binding.swipeRefreshLayout.isRefreshing = false;
            shufflelistItems();
        }
    }

    /* set recycler view adapter for the cake list */
    private fun populateData() {
        val cakesAdapter = viewModel.response.value?.let { CakesAdapter(it, this) }
        binding.recyclerView.adapter = cakesAdapter
    }

    /* shuffle the list on pull to refresh */
    private fun shufflelistItems() {
        val adapter = viewModel.response.value?.let { CakesAdapter(it.shuffled(), this) }
        binding.recyclerView.adapter = adapter
    }

    override fun onCakeClick(cakeDataModel: CakeDataModel) {
        showDescriptionDialog(cakeDataModel.desc)
    }

    /* Show alert dialog with cake description error dialog */
    private fun showDescriptionDialog(description: String) {
        val alertDialog: android.app.AlertDialog =
            android.app.AlertDialog.Builder(this@CakesActivity).create()
        alertDialog.setMessage(description)
        alertDialog.show()
    }
}