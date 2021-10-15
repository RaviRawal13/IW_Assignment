package com.ravirawal.iw_assignment.ui.grid

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.ravirawal.iw_assignment.R
import com.ravirawal.iw_assignment.databinding.FragmentOrdersGridBinding
import com.ravirawal.iw_assignment.databinding.LoadingShimmerGridLayoutBinding
import com.ravirawal.iw_assignment.shared_vm.OrdersViewModelFactory
import com.ravirawal.iw_assignment.shared_vm.OrdersViewModel
import com.ravirawal.iw_assignment.ui.grid.adapter.OrderGridAdapter
import com.ravirawal.iw_assignment.usecase.LOADING
import com.ravirawal.iw_assignment.usecase.OrdersUseCaseFactory
import com.ravirawal.iw_assignment.utils.GridSpacingItemDecoration
import com.ravirawal.iw_assignment.utils.gone
import com.ravirawal.iw_assignment.utils.visible

const val SPAN_COUNT = 3
const val INCLUDE_EDGE = true

class OrdersGridFragment : Fragment(R.layout.fragment_orders_grid) {

    private lateinit var binding: FragmentOrdersGridBinding

    private lateinit var ordersViewModel: OrdersViewModel

    private val ordersViewModelFactory by lazy {
        OrdersViewModelFactory(
            OrdersUseCaseFactory.getOrdersUseCase()
        )
    }

    private val loading by lazy {
        LoadingShimmerGridLayoutBinding.inflate(
            LayoutInflater.from(binding.root.context),
            binding.root
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
            when {
                it.isEmpty() -> {
                    stopLoading()
                    orderListAdapter.submitList(it)
                }
                it.first().name == LOADING -> {
                    startLoading()
                }
                else -> {
                    stopLoading()
                    orderListAdapter.submitList(it)
                }
            }
        }

        ordersViewModel.fetchOrders()
    }

    private fun startLoading() {
        binding.recyclerViewOrdersGrid.gone()
        (loading.shimmerSources).let {
            it.startShimmer()
            it.visible()
        }
    }

    private fun stopLoading() {
        binding.recyclerViewOrdersGrid.visible()
        (loading.shimmerSources).let {
            it.gone()
            it.stopShimmer()
        }
    }
}