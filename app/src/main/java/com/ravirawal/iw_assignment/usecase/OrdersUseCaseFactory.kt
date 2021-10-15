package com.ravirawal.iw_assignment.usecase

import com.ravirawal.iw_assignment.repository.OrderRepositoryFactory

object OrdersUseCaseFactory {
    fun getOrdersUseCase() =
        OrdersUseCase(OrderRepositoryFactory.getOrderRepository())
}