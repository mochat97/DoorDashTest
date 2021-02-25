package com.mshaw.doordashtest.ui.restaurantlist

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.TypedValue
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mshaw.doordashtest.R
import com.mshaw.doordashtest.adapters.RestaurantListAdapter
import com.mshaw.doordashtest.databinding.ActivityRestaurantListBinding
import com.mshaw.doordashtest.databinding.AlertDialogCoordinatesBinding
import com.mshaw.doordashtest.models.RestaurantListResponse
import com.mshaw.doordashtest.models.Store
import com.mshaw.doordashtest.ui.restaurantdetails.RestaurantDetailsActivity
import com.mshaw.doordashtest.util.EqualSpacingItemDecorator
import com.mshaw.doordashtest.util.extensions.asString
import com.mshaw.doordashtest.util.extensions.get
import com.mshaw.doordashtest.util.extensions.set
import com.mshaw.doordashtest.util.extensions.withCancelButton
import com.mshaw.doordashtest.util.state.State
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class RestaurantListActivity: AppCompatActivity(), Listener {

    @Inject
    lateinit var sharedPreferences: SharedPreferences
    private val viewModel: RestaurantListViewModel by viewModels()

    private val binding: ActivityRestaurantListBinding by lazy {
        ActivityRestaurantListBinding.inflate(layoutInflater)
    }

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
                    val response = it.value
                    binding.progressBar.hide()

                    val stores = response.stores
                    stores.forEach { store ->
                        store.isFavorited = sharedPreferences.get<Boolean>(store.id.toString()) == true
                    }

                    updateRecyclerView(stores)
                }
                is State.Error -> {
                    binding.progressBar.hide()
                    binding.restaurantListRecyclerView.isVisible = false
                }
            }
        }

        setContentView(binding.root)
        setTitle(R.string.restaurant_list)
        setSupportActionBar(binding.toolbar)

        setupBinding()

        viewModel.fetchRestaurantList(37.422740, -122.139956, 0, 50)
    }

    private fun updateRecyclerView(stores: List<Store>) {
        binding.restaurantListRecyclerView.isVisible = stores.isNotEmpty()
        adapter.restaurants = stores
    }

    private fun setupBinding() {
        val dp = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,16f, resources.displayMetrics).toInt()
        val recyclerView = binding.restaurantListRecyclerView
        val fab = binding.fab

        recyclerView.addItemDecoration(EqualSpacingItemDecorator(dp))
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (dy > 0) {
                    fab.hide()
                } else {
                    fab.show()
                }
            }
        })

        fab.setOnClickListener {
            val coordinatesView = AlertDialogCoordinatesBinding.inflate(layoutInflater)
            val dialog = AlertDialog.Builder(this).setView(coordinatesView.root)
                .setTitle("Enter coordinates")
                .withCancelButton()
                .setPositiveButton("Search", null)
                .create()

            dialog.setOnShowListener {
                dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener {
                    var isValid = true
                    val latitude = try {
                        coordinatesView.latitudeEditText.asString().toDouble()
                    } catch (e: Exception) {
                        coordinatesView.tilLatitudeEditText.error = "Invalid input"
                        isValid = false
                        0.0
                    }

                    val longitude = try {
                        coordinatesView.longitudeEditText.asString().toDouble()
                    } catch (e: Exception) {
                        coordinatesView.tilLongitudeEditText.error = "Invalid input"
                        isValid = false
                        0.0
                    }

                    if (isValid) {
                        viewModel.fetchRestaurantList(latitude, longitude, 0, 50)
                        dialog.dismiss()
                    }
                }
            }

            dialog.show()
        }
    }

    override fun onStoreSelected(store: Store) {
        startActivity(Intent(this, RestaurantDetailsActivity::class.java).apply {
            putExtra(RestaurantDetailsActivity.STORE, store)
        })
    }

    override fun onFavorited(id: String, isFavorited: Boolean) {
        sharedPreferences[id] = isFavorited
    }
}

interface Listener {
    fun onStoreSelected(store: Store)
    fun onFavorited(id: String, isFavorited: Boolean)
}