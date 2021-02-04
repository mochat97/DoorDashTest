package com.mshaw.doordashtest.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mshaw.doordashtest.ui.restaurantlist.Listener
import com.mshaw.doordashtest.databinding.ItemRestaurantInfoBinding
import com.mshaw.doordashtest.models.Store
import java.util.*

class RestaurantListAdapter(val listener: Listener, private val restaurants: MutableList<Store> = mutableListOf()): RecyclerView.Adapter<RestaurantListAdapter.ViewHolder>() {
    fun addStores(stores: List<Store>) {
        restaurants += stores
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemRestaurantInfoBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(restaurants[position])
    }

    override fun getItemCount() = restaurants.size

    inner class ViewHolder(private val binding: ItemRestaurantInfoBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(store: Store) {
            binding.store = store
            binding.travelTime.text = store.getDistanceFromConsumerFormatted(Date())
            binding.root.setOnClickListener {
                listener.onStoreSelected(store)
            }
        }
    }
}