package com.ravirawal.iw_assignment.ui.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.ravirawal.iw_assignment.R
import com.ravirawal.iw_assignment.databinding.FragmentOrdersListBinding
import com.ravirawal.iw_assignment.databinding.LoadingShimmerListLayoutBinding
import com.ravirawal.iw_assignment.repository.OrdersRepository
import com.ravirawal.iw_assignment.retrofit.ServiceHelper
import com.ravirawal.iw_assignment.shared_vm.LOADING
import com.ravirawal.iw_assignment.shared_vm.OrdersViewModel
import com.ravirawal.iw_assignment.shared_vm.OrdersViewModelFactory
import com.ravirawal.iw_assignment.ui.list.adapter.OrderListAdapter
import com.ravirawal.iw_assignment.utils.gone
import com.ravirawal.iw_assignment.utils.visible

class OrdersListFragment : Fragment(R.layout.fragment_orders_list) {

    private lateinit var binding: FragmentOrdersListBinding

    private lateinit var ordersViewModel: OrdersViewModel

    private val orderListAdapter = OrderListAdapter()

    private val ordersViewModelFactory by lazy {
        OrdersViewModelFactory(
            OrdersRepository(
                ServiceHelper.getAPIHelper()
            )
        )
    }

    private val loading by lazy {
        LoadingShimmerListLayoutBinding.inflate(
            LayoutInflater.from(binding.root.context),
            binding.root
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentOrdersListBinding.bind(view)

        activity?.let {
            ordersViewModel =
                ViewModelProvider(
                    it,
                    ordersViewModelFactory
                ).get(OrdersViewModel::class.java)
        }

        binding.recyclerViewOrdersList.adapter = orderListAdapter

        ordersViewModel.ordersLiveData.observe(viewLifecycleOwner) {
            when {
                it.isEmpty() -> {
                    orderListAdapter.differ.submitList(emptyList())
                }
                it.first().name == LOADING -> {
                    binding.recyclerViewOrdersList.gone()
                    startLoading()
                }
                else -> {
                    binding.recyclerViewOrdersList.visible()
                    stopLoading()
                    orderListAdapter.differ.submitList(it)
                }
            }
        }

        ordersViewModel.fetchOrders()
    }

    private fun startLoading() {
        (loading.shimmerSources).let {
            it.startShimmer()
            it.visible()

        }
    }

    private fun stopLoading() {
        (loading.shimmerSources).let {
            it.gone()
            it.stopShimmer()
        }
    }
}