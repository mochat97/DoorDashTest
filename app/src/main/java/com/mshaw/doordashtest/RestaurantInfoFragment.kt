package com.mshaw.doordashtest

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI.setupActionBarWithNavController
import com.mshaw.doordashtest.databinding.ActivityRestaurantInfoBinding
import com.mshaw.doordashtest.models.Store
import java.lang.IllegalStateException

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class RestaurantInfoFragment : Fragment() {
    companion object {
        const val STORE = "store"
    }

    lateinit var store: Store
    private var _binding: ActivityRestaurantInfoBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        store = arguments?.getParcelable(STORE) ?: throw IllegalStateException("$STORE must not be null")
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        _binding = ActivityRestaurantInfoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //binding.store = store
        //binding.toolbar.setNavigationIcon(R.drawable.ic_back_button)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}