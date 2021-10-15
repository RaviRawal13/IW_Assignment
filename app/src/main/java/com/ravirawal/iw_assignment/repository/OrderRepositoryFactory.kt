package com.ravirawal.iw_assignment.repository

import com.ravirawal.iw_assignment.retrofit.ServiceHelper

object OrderRepositoryFactory {
    fun getOrderRepository() =
        OrdersRepository(ServiceHelper.getAPIHelper())
}