package com.ravirawal.iw_assignment.ui.list

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.ravirawal.iw_assignment.R
import com.ravirawal.iw_assignment.databinding.FragmentOrdersListBinding
import com.ravirawal.iw_assignment.repository.OrdersRepository
import com.ravirawal.iw_assignment.retrofit.ServiceHelper
import com.ravirawal.iw_assignment.shared_vm.OrdersViewModel
import com.ravirawal.iw_assignment.shared_vm.OrdersViewModelFactory

class OrdersListFragment : Fragment(R.layout.fragment_orders_list) {

    private lateinit var binding: FragmentOrdersListBinding

    private lateinit var ordersViewModel: OrdersViewModel

    private val ordersViewModelFactory by lazy {
        OrdersViewModelFactory(
            OrdersRepository(
                ServiceHelper.getAPIHelper()
            )
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
    }
}