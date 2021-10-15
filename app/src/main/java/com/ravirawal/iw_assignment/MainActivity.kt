package com.ravirawal.iw_assignment

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.ravirawal.iw_assignment.databinding.ActivityMainBinding
import com.ravirawal.iw_assignment.repository.OrdersRepository
import com.ravirawal.iw_assignment.retrofit.ServiceHelper
import com.ravirawal.iw_assignment.shared_vm.OrdersViewModel
import com.ravirawal.iw_assignment.shared_vm.OrdersViewModelFactory
import com.ravirawal.iw_assignment.usecase.OrdersUseCaseFactory
import com.ravirawal.iw_assignment.utils.SORTING_CLEAR
import com.ravirawal.iw_assignment.utils.delayOnLifeCycle

private const val DELAY_ON_SEARCH = 400L

class MainActivity : AppCompatActivity() {

    private val singleItems: Array<String> by lazy {
        resources.getStringArray(R.array.sorting_options)
    }

    private var sortingOrder = SORTING_CLEAR

    private fun filterDialog() {
        MaterialAlertDialogBuilder(this)
            .setTitle(getString(R.string.sort_by))
            .setSingleChoiceItems(singleItems, sortingOrder)
            { dialog, which ->
                sortingOrder = which
                Toast.makeText(
                    this,
                    getString(R.string.sorting_by_message) + singleItems[which],
                    Toast.LENGTH_SHORT
                ).show()

                onSortingChange(sortingOrder)

                dialog.dismiss()
            }
            .show()
    }

    private val ordersViewModel: OrdersViewModel by lazy {
        ViewModelProvider(
            this,
            ordersViewModelFactory
        ).get(OrdersViewModel::class.java)
    }

    private val ordersViewModelFactory by lazy {
        OrdersViewModelFactory(
            OrdersUseCaseFactory.getOrdersUseCase()
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.navView.setupWithNavController(findNavController(R.id.nav_host_fragment_activity_main))

        binding.searchViewOrdersMain.setOnQueryTextListener(object :
            SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                binding.searchViewOrdersMain.delayOnLifeCycle(DELAY_ON_SEARCH) {
                    onTextChanged(newText ?: "")
                }
                return true
            }

        })

        binding.topAppBar.setOnMenuItemClickListener {
            filterDialog()
            true
        }
    }

    private fun onTextChanged(newText: String) {
        ordersViewModel.filterOrders(newText)
    }

    private fun onSortingChange(sortOrder: Int) {
        ordersViewModel.sortOrders(sortOrder)
    }
}