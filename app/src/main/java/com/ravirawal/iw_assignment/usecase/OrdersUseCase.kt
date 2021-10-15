package com.ravirawal.iw_assignment.usecase

import com.ravirawal.iw_assignment.model_ui.OrderedItem
import com.ravirawal.iw_assignment.network.Result
import com.ravirawal.iw_assignment.network.model.OrdersListResponse
import com.ravirawal.iw_assignment.repository.OrdersRepository
import com.ravirawal.iw_assignment.utils.*

private const val LOADING_IMAGE_ID = 1
const val LOADING = "LOADING"

class OrdersUseCase(private val ordersRepository: OrdersRepository) {

    var query: String = ""

    var sortingOrder: Int = SORTING_CLEAR

    val statusWhileLoading = listOf(
        OrderedItem(
            name = LOADING,
            extra = LOADING,
            price = LOADING,
            imageId = LOADING_IMAGE_ID
        )
    )

    var orderListCache: List<OrderedItem>? = null


    suspend fun getOrdersList(
    ): List<OrderedItem> {
        val ordersList: List<OrderedItem> = when (val orderResponse: Result<OrdersListResponse> =
            ordersRepository.getOrdersList()) {
            is Result.Success -> {
                val items = orderResponse.data.data?.items
                items?.mapIndexedNotNull { index, item ->
                    OrderedItem(
                        extra = item?.extra,
                        name = item?.name,
                        price = item?.price,
                        imageId = index
                    )
                } ?: emptyList()
            }
            is Result.Failure -> {
                emptyList()
            }
            is Result.LOAD -> {
                statusWhileLoading
            }
            else -> {
                emptyList()
            }
        }
        this.orderListCache = ordersList
        var list: List<OrderedItem> = orderListCache?.map { it.copy() } ?: emptyList()

        if (query.isNotEmpty()) {
            list = filterOrders(query)
        }

        if (sortingOrder != SORTING_CLEAR) {
            list = sortOrders(sortingOrder)
        }

        return list
    }

    fun filterOrders(newText: String): List<OrderedItem> {
        query = newText
        val list = orderListCache?.map { it.copy() }
        val filteredOrders: List<OrderedItem> = list?.filter {
            it.name?.contains(newText, true) ?: false
        } ?: emptyList()

        return filteredOrders
    }

    fun sortOrders(sortOrder: Int): List<OrderedItem> {
        sortingOrder = sortOrder
        var list = orderListCache?.map { it.copy() }

        if (query.isNotEmpty()) {
            list = list?.filter {
                it.name?.contains(query, true) ?: false
            } ?: emptyList()
        }

        val sortAsc: Boolean

        val sortType: String = when (sortOrder) {
            SORTING_CLEAR -> {
                sortAsc = true
                ""
            }
//            SORTING_PRICE_ASC -> {
//                sortAsc = true
//                "price"
//            }
//            SORTING_PRICE_DESC -> {
//                sortAsc = false
//                "price"
//            }
            SORTING_NAME_ASC -> {
                sortAsc = true
                "name"
            }
            SORTING_NAME_DESC -> {
                sortAsc = false
                "name"
            }
            SORTING_SAME_DAY_SHIPPING -> {
                sortAsc = true
                "extra"
            }
            else -> {
                sortAsc = true
                ""
            }
        }

        return if (sortAsc)
            list?.sortedBy { sortType } ?: emptyList()
        else {
            list?.sortedBy { sortType }?.reversed() ?: emptyList()
        }
    }

}