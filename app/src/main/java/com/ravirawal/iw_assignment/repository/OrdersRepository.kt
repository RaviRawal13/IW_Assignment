package com.ravirawal.iw_assignment.repository

import com.ravirawal.iw_assignment.network.Error
import com.ravirawal.iw_assignment.network.Result
import com.ravirawal.iw_assignment.retrofit.IApiHelper
import com.ravirawal.iw_assignment.network.model.OrdersListResponse

class OrdersRepository(private val service: IApiHelper) {

    suspend fun getOrdersList(
    ): Result<OrdersListResponse> {
        val response = service.getOrdersList()
        return if (response.isSuccessful && response.body() is OrdersListResponse) {
            Result.Success(
                response.body() as OrdersListResponse,
                response.message(),
                response.code()
            )
        } else {
            Result.Failure(
                Error.APIError,
                response.message(),
                response.code()
            )
        }
    }

}
