package com.mshaw.doordashtest.ui.restaurantlist

import android.os.Bundle
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.mshaw.doordashtest.R
import com.mshaw.doordashtest.RestaurantInfoFragment
import com.mshaw.doordashtest.adapters.RestaurantListAdapter
import com.mshaw.doordashtest.databinding.FragmentRestaurantListBinding
import com.mshaw.doordashtest.models.Store
import com.mshaw.doordashtest.util.EqualSpacingItemDecorator
import com.mshaw.doordashtest.util.state.State
import dagger.hilt.android.AndroidEntryPoint

/*
@AndroidEntryPoint
class RestaurantListFragment: Fragment(), Listener {
    lateinit var binding: FragmentRestaurantListBinding
    private val viewModel: RestaurantListViewModel by viewModels()
    private val adapter: RestaurantListAdapter by lazy {
        RestaurantListAdapter(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRestaurantListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val dp = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,16f, resources.displayMetrics).toInt()
        binding.restaurantListRecyclerView.addItemDecoration(EqualSpacingItemDecorator(dp))
        binding.restaurantListRecyclerView.adapter = adapter
        binding.restaurantListRecyclerView.layoutManager = LinearLayoutManager(requireContext())

        viewModel.restaurantListLiveData.observe(viewLifecycleOwner) {
            binding.progressBar.isVisible = it == State.Loading
            when (it) {
                State.Loading -> {

                }
                is State.Success -> {
                    adapter.addStores(it.value)
                }
                is State.Error -> {

                }
            }
        }

        viewModel.fetchRestaurantList(37.422740, -122.139956, 0, 50)
    }

    override fun onStoreSelected(store: Store) {
        val bundle = bundleOf(RestaurantInfoFragment.STORE to store)
        view?.findNavController()?.navigate(R.id.RestaurantFragment, bundle)
    }
}

 */