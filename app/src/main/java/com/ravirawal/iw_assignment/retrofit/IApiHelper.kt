package com.ravirawal.iw_assignment.retrofit

import com.ravirawal.iw_assignment.network.model.OrdersListResponse
import retrofit2.Response

interface IApiHelper {

    suspend fun getOrdersList(): Response<OrdersListResponse>

    class ApiHelperImpl(private val apiService: ApiService) : IApiHelper {

        override suspend fun getOrdersList(): Response<OrdersListResponse> {
            return apiService.getOrdersList()
        }
    }
}
