package com.ravirawal.iw_assignment.repository

import com.ravirawal.iw_assignment.network.Error
import com.ravirawal.iw_assignment.network.Result
import com.ravirawal.iw_assignment.retrofit.IApiHelper
import com.ravirawal.iw_assignment.network.model.OrdersListResponse

private const val MESSAGE_CACHE = "Cache"
private const val CODE_CACHE = 200

class OrdersRepository(private val service: IApiHelper) {

    var cache: OrdersListResponse? = null

    suspend fun getOrdersList(
    ): Result<OrdersListResponse> {
        if (cache != null) {
            cache?.let {
                return Result.Success(
                    it,
                    MESSAGE_CACHE,
                    CODE_CACHE
                )
            }
        }

        val response = service.getOrdersList()
        return if (response.isSuccessful && response.body() is OrdersListResponse) {
            cache = response.body() as OrdersListResponse
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
