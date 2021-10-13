package com.ravirawal.iw_assignment.retrofit

import com.ravirawal.iw_assignment.network.model.OrdersListResponse
import com.ravirawal.iw_assignment.retrofit.API_ORDERS
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {

    /**
     * Fetch List of orders using GET API Call on given Url
     * Url would be something like this /v3/b6a30bb0-140f-4966-8608-1dc35fa1fadc
     */
    @GET(API_ORDERS)
    suspend fun getOrdersList(): Response<OrdersListResponse>
}