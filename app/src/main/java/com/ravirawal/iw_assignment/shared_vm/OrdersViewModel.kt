package com.ravirawal.iw_assignment.shared_vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ravirawal.iw_assignment.model_ui.OrderedItem
import com.ravirawal.iw_assignment.network.Result
import com.ravirawal.iw_assignment.network.model.OrdersListResponse
import com.ravirawal.iw_assignment.repository.OrdersRepository
import kotlinx.coroutines.launch

private const val LOADING_IMAGE_ID = 1
const val LOADING = "LOADING"

class OrdersViewModel(
    private val ordersRepository: OrdersRepository,
) : ViewModel() {

    var ordersLiveData = MutableLiveData<List<OrderedItem>>()

    var query: String = ""

    fun fetchOrders() {
        viewModelScope.launch {

            ordersLiveData.value =
                listOf(
                    OrderedItem(
                        name = LOADING,
                        extra = LOADING,
                        price = LOADING,
                        imageId = LOADING_IMAGE_ID
                    )
                )

            when (val ordersList: Result<OrdersListResponse> = ordersRepository.getOrdersList()) {
                is Result.Success -> {

                    var items = ordersList.data.data?.items

                    if (query.isNotEmpty()) {
                        items = items?.filter {
                            it?.name?.contains(query, true) ?: false
                        }
                    }

                    ordersLiveData.value =
                        items?.mapIndexedNotNull { index, item ->
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

    fun filterOrders(newText: String) {
        query = newText
        val filteredOrders: List<OrderedItem>? = ordersRepository.cache?.data?.items?.filter {
            it?.name?.contains(newText, true) ?: false
        }?.mapIndexedNotNull { index, item ->
            OrderedItem(
                extra = item?.extra,
                name = item?.name,
                price = item?.price,
                imageId = index
            )
        }

        filteredOrders?.let {
            ordersLiveData.value = it
        }
    }


}