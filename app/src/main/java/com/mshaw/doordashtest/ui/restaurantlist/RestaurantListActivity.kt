package com.mshaw.doordashtest.ui.restaurantlist

import android.content.Intent
import android.os.Bundle
import android.util.TypedValue
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.mshaw.doordashtest.adapters.RestaurantListAdapter
import com.mshaw.doordashtest.databinding.ActivityRestaurantListBinding
import com.mshaw.doordashtest.models.Store
import com.mshaw.doordashtest.ui.restaurantinfo.RestaurantInfoActivity
import com.mshaw.doordashtest.util.EqualSpacingItemDecorator
import com.mshaw.doordashtest.util.state.State
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RestaurantListActivity: AppCompatActivity(), Listener {
    lateinit var binding: ActivityRestaurantListBinding
    private val viewModel: RestaurantListViewModel by viewModels()
    private val adapter: RestaurantListAdapter by lazy {
        RestaurantListAdapter(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.restaurantListLiveData.observe(this) {
            when (it) {
                State.Loading -> {
                    binding.progressBar.show()
                }
                is State.Success -> {
                    binding.progressBar.hide()
                    adapter.addStores(it.value)
                }
                is State.Error -> {
                    binding.progressBar.hide()
                }
            }
        }

        binding = ActivityRestaurantListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupBinding()

        viewModel.fetchRestaurantList(37.422740, -122.139956, 0, 50)
    }

    private fun setupBinding() {
        val dp = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,16f, resources.displayMetrics).toInt()
        binding.restaurantListRecyclerView.addItemDecoration(EqualSpacingItemDecorator(dp))
        binding.restaurantListRecyclerView.adapter = adapter
        binding.restaurantListRecyclerView.layoutManager = LinearLayoutManager(this)
    }

    override fun onStoreSelected(store: Store) {
        startActivity(Intent(this, RestaurantInfoActivity::class.java).apply {
            putExtra(RestaurantInfoActivity.STORE, store)
        })
    }
}

interface Listener {
    fun onStoreSelected(store: Store)
}