package com.ravirawal.iw_assignment.ui.grid

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.ravirawal.iw_assignment.R
import com.ravirawal.iw_assignment.databinding.FragmentOrdersGridBinding
import com.ravirawal.iw_assignment.repository.OrdersRepository
import com.ravirawal.iw_assignment.retrofit.ServiceHelper
import com.ravirawal.iw_assignment.shared_vm.LOADING
import com.ravirawal.iw_assignment.shared_vm.OrdersViewModelFactory
import com.ravirawal.iw_assignment.shared_vm.OrdersViewModel
import com.ravirawal.iw_assignment.ui.grid.adapter.OrderGridAdapter
import com.ravirawal.iw_assignment.utils.GridSpacingItemDecoration

const val SPAN_COUNT = 3
const val INCLUDE_EDGE = true

class OrdersGridFragment : Fragment(R.layout.fragment_orders_grid) {

    private lateinit var binding: FragmentOrdersGridBinding

    private lateinit var ordersViewModel: OrdersViewModel

    private val ordersViewModelFactory by lazy {
        OrdersViewModelFactory(
            OrdersRepository(
                ServiceHelper.getAPIHelper()
            )
        )
    }

    private val orderListAdapter = OrderGridAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentOrdersGridBinding.bind(view)
        activity?.let {
            ordersViewModel =
                ViewModelProvider(
                    it,
                    ordersViewModelFactory
                ).get(OrdersViewModel::class.java)
        }

        val marginItem = resources.getDimension(R.dimen.margin_parent_grid_item).toInt()

        binding.recyclerViewOrdersGrid.addItemDecoration(
            GridSpacingItemDecoration(
                SPAN_COUNT,
                marginItem,
                INCLUDE_EDGE
            )
        )

        binding.recyclerViewOrdersGrid.adapter = orderListAdapter

        ordersViewModel.ordersLiveData.observe(viewLifecycleOwner) {
            when (it.first().name) {
                LOADING -> {

                }
                else -> {
                    orderListAdapter.differ.submitList(it)
                }
            }
        }

        ordersViewModel.fetchOrders()
    }
}