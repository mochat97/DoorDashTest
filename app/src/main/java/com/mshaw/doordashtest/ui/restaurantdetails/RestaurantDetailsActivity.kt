package com.mshaw.doordashtest.ui.restaurantdetails

import android.app.ProgressDialog
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NavUtils
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.MarkerOptions
import com.mshaw.doordashtest.R
import com.mshaw.doordashtest.models.Store
import java.lang.IllegalStateException
import com.google.android.gms.maps.CameraUpdateFactory
import com.mshaw.doordashtest.databinding.ActivityRestaurantDetailsBinding
import com.mshaw.doordashtest.util.extensions.toLatLng
import com.mshaw.doordashtest.util.state.State
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RestaurantDetailsActivity: AppCompatActivity(), OnMapReadyCallback {
    companion object {
        const val STORE = "store"
    }

    lateinit var store: Store
    private val binding: ActivityRestaurantDetailsBinding by lazy {
        ActivityRestaurantDetailsBinding.inflate(layoutInflater)
    }

    private val viewModel: RestaurantDetailsViewModel by viewModels()
    private val progressDialog: ProgressDialog by lazy {
        ProgressDialog(this).apply {
            setMessage("Loading...")
        }
    }

    private lateinit var mapView: SupportMapFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        store = intent?.getParcelableExtra(STORE) ?: throw IllegalStateException("$STORE must not be null")
        binding.lifecycleOwner = this
        binding.store = store
        binding.liveData = viewModel.restaurantDetailsDBLiveData
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.collapsingToolbar.isTitleEnabled = store.headerImgUrl.isNotEmpty()
        title = store.name
        mapView = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapView.onCreate(savedInstanceState)

        viewModel.restaurantDetailsLiveData.observe(this) {
            when (it) {
                State.Loading -> {
                    progressDialog.show()
                }
                is State.Success -> {
                    progressDialog.dismiss()
                    mapView.getMapAsync(this)
                }
                is State.Error -> {
                    progressDialog.dismiss()
                }
            }
        }

        viewModel.getRestaurantDetails(store.id.toString())
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                NavUtils.navigateUpFromSameTask(this)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onMapReady(map: GoogleMap) {
        val latLng = store.location.toLatLng()
        map.addMarker(
            MarkerOptions()
                .position(latLng)
        )
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15f))
        mapView.onResume()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mapView.onSaveInstanceState(outState)
    }

    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        mapView.onPause()
    }

    override fun onStart() {
        super.onStart()
        mapView.onStart()
    }

    override fun onStop() {
        super.onStop()
        mapView.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView.onLowMemory()
    }
}