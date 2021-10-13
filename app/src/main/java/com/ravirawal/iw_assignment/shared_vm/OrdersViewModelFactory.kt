package com.ravirawal.iw_assignment.shared_vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ravirawal.iw_assignment.repository.OrdersRepository

@Suppress("UNCHECKED_CAST")
class OrdersViewModelFactory(
    private val api: OrdersRepository
) : ViewModelProvider.NewInstanceFactory(){
 
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return OrdersViewModel(api) as T
    }
}