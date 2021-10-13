package com.ravirawal.iw_assignment.shared_vm

import androidx.lifecycle.ViewModel
import com.ravirawal.iw_assignment.repository.OrdersRepository

private const val PREFETCH_DISTANCE = 5

class OrdersViewModel(
    private val ordersRepository: OrdersRepository,
) : ViewModel() {



}