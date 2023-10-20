package com.jaws.challengeappilham.data.repository

import com.jaws.challengeappilham.data.network.api.datasource.RestaurantApiDataSource
import com.jaws.challengeappilham.data.network.api.model.order.OrderRequest
import com.jaws.challengeappilham.data.network.api.model.order.OrdersResponse
import com.jaws.challengeappilham.utils.ResultWrapper
import com.jaws.challengeappilham.utils.proceedFlow
import kotlinx.coroutines.flow.Flow
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

interface OrderRepository {
    suspend fun createOrder(orderRequest: OrderRequest): Flow<ResultWrapper<OrdersResponse>>
}

class OrderRepositoryImpl(
    private val apiDataSource: RestaurantApiDataSource
) : OrderRepository{
    override suspend fun createOrder(orderRequest: OrderRequest): Flow<ResultWrapper<OrdersResponse>> {
        return proceedFlow {
            apiDataSource.createOrder(orderRequest)
        }
    }
}