package com.ravirawal.iw_assignment.shared_vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ravirawal.iw_assignment.model_ui.OrderedItem
import com.ravirawal.iw_assignment.network.Result
import com.ravirawal.iw_assignment.network.model.OrdersListResponse
import com.ravirawal.iw_assignment.repository.OrdersRepository
import kotlinx.coroutines.launch

private const val PREFETCH_DISTANCE = 5
const val LOADING = "LOADING"

class OrdersViewModel(
    private val ordersRepository: OrdersRepository,
) : ViewModel() {

    var ordersLiveData = MutableLiveData<List<OrderedItem>>()

    fun fetchOrders() {
        viewModelScope.launch {
            when (val ordersList: Result<OrdersListResponse> = ordersRepository.getOrdersList()) {
                is Result.Success -> {
                    ordersLiveData.value =
                        ordersList.data.data?.items?.mapIndexedNotNull { index, item ->
                            OrderedItem(
                                extra = item?.extra,
                                name = item?.name,
                                price = item?.price,
                                imageId = index
                            )
                        }
                }
                is Result.Failure -> {
                    ordersLiveData.value = emptyList()
                }

                is Result.LOAD -> {
                    ordersLiveData.value =
                        listOf(
                            OrderedItem(
                                name = LOADING,
                                extra = LOADING,
                                price = LOADING,
                                imageId = 1
                            )
                        )
                }
                else -> {
                    ordersLiveData.value = emptyList()
                }
            }
        }
    }


}