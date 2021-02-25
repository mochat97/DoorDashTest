package com.mshaw.doordashtest.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mshaw.doordashtest.R
import com.mshaw.doordashtest.ui.restaurantlist.Listener
import com.mshaw.doordashtest.databinding.ItemRestaurantInfoBinding
import com.mshaw.doordashtest.models.Store
import java.util.*

class RestaurantListAdapter(val listener: Listener): RecyclerView.Adapter<RestaurantListAdapter.ViewHolder>() {
    var restaurants: List<Store> = emptyList()
        set(value) {
            field = value
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

            binding.favoriteButton.setOnClickListener {
                val newIsFavorited = !store.isFavorited
                store.isFavorited = newIsFavorited
                val resource = if (newIsFavorited) {
                    R.drawable.ic_favorite_on
                } else {
                    R.drawable.ic_favorite_off
                }

                binding.favoriteButton.setBackgroundResource(resource)
                listener.onFavorited(store.id.toString(), newIsFavorited)
            }
        }
    }
}