package com.ravirawal.iw_assignment

import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.ravirawal.iw_assignment.databinding.ActivityMainBinding
import com.ravirawal.iw_assignment.repository.OrdersRepository
import com.ravirawal.iw_assignment.retrofit.ServiceHelper
import com.ravirawal.iw_assignment.shared_vm.OrdersViewModel
import com.ravirawal.iw_assignment.shared_vm.OrdersViewModelFactory
import com.ravirawal.iw_assignment.utils.delayOnLifeCycle

class MainActivity : AppCompatActivity() {

    private lateinit var ordersViewModel: OrdersViewModel

    private val ordersViewModelFactory by lazy {
        OrdersViewModelFactory(
            OrdersRepository(
                ServiceHelper.getAPIHelper()
            )
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        navView.setupWithNavController(navController)

        ordersViewModel =
            ViewModelProvider(
                this,
                ordersViewModelFactory
            ).get(OrdersViewModel::class.java)

        binding.topAppBar.setNavigationOnClickListener {
            when (it.id) {
                R.id.filter -> {
                }
                else -> {
                }
            }
        }

        binding.searchViewOrdersMain.setOnQueryTextListener(object :
            SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                binding.searchViewOrdersMain.delayOnLifeCycle(400L) {
                    onTextChanged(newText ?: "")
                }
                return true
            }

        })

        binding.topAppBar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.filter -> {
                    // Handle favorite icon press
                    true
                }

                else -> false
            }
        }
    }

    fun onTextChanged(newText: String) {
        ordersViewModel.filterOrders(newText)
    }
}