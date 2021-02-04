package com.mshaw.doordashtest.ui.restaurantinfo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.mshaw.doordashtest.R
import com.mshaw.doordashtest.databinding.ActivityRestaurantInfoBinding
import com.mshaw.doordashtest.models.Store
import java.lang.IllegalStateException
import com.google.android.gms.maps.CameraUpdateFactory
import com.mshaw.doordashtest.util.extensions.toLatLng
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RestaurantInfoActivity: AppCompatActivity(), OnMapReadyCallback {
    companion object {
        const val STORE = "store"
    }

    lateinit var store: Store
    private lateinit var binding: ActivityRestaurantInfoBinding
    val viewModel: RestaurantInfoViewModel by lazy {
        RestaurantInfoViewModel(store)
    }

    private lateinit var mapView: SupportMapFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        store = intent?.getParcelableExtra(STORE) ?: throw IllegalStateException("$STORE must not be null")
        binding = ActivityRestaurantInfoBinding.inflate(layoutInflater)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.collapsingToolbar.isTitleEnabled = store.headerImgUrl.isNotEmpty()
        mapView = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync(this)
    }

    override fun onMapReady(map: GoogleMap) {
        val latLng = store.location.toLatLng()
        map.addMarker(
            MarkerOptions()
                .position(latLng)
                .title("Marker")
        )
        map.addMarker(
            MarkerOptions().position(latLng).title(store.name)
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