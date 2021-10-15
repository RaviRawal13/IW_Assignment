package com.ravirawal.iw_assignment.shared_vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ravirawal.iw_assignment.model_ui.OrderedItem
import com.ravirawal.iw_assignment.usecase.OrdersUseCase
import com.ravirawal.iw_assignment.utils.SORTING_CLEAR
import kotlinx.coroutines.launch

class OrdersViewModel(
    private val ordersUseCase: OrdersUseCase,
) : ViewModel() {

    var ordersLiveData = MutableLiveData<List<OrderedItem>>()

    fun fetchOrders() {
        viewModelScope.launch {
            ordersLiveData.value = ordersUseCase.statusWhileLoading

            ordersLiveData.value = ordersUseCase.getOrdersList()
        }
    }

    fun filterOrders(newText: String) {
        ordersLiveData.value = ordersUseCase.filterOrders(newText)
    }

    fun sortOrders(order: Int) {
        ordersLiveData.value = ordersUseCase.sortOrders(order)
    }


}