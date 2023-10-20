package com.jaws.challengeappilham.data.network.api.datasource

import com.jaws.challengeappilham.data.network.api.model.category.CategoriesResponse
import com.jaws.challengeappilham.data.network.api.model.menu.MenusResponse
import com.jaws.challengeappilham.data.network.api.model.order.OrderRequest
import com.jaws.challengeappilham.data.network.api.model.order.OrdersResponse
import com.jaws.challengeappilham.data.network.api.service.RestaurantService
import retrofit2.Call

interface RestaurantApiDataSource {
    suspend fun getMenus(category: String? = null): MenusResponse
    suspend fun getCategories(): CategoriesResponse
    suspend fun createOrder(orderRequest: OrderRequest): OrdersResponse
}

class RestaurantApiDataSourceImpl(
    private val service: RestaurantService
) : RestaurantApiDataSource{
    override suspend fun getMenus(category: String?): MenusResponse {
        return service.getMenus(category)
    }

    override suspend fun getCategories(): CategoriesResponse {
        return service.getCategories()
    }

    override suspend fun createOrder(orderRequest: OrderRequest): OrdersResponse {
        return service.createOrder(orderRequest)
    }
}