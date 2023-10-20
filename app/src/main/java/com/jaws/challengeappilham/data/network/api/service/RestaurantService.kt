package com.jaws.challengeappilham.data.network.api.service

import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.jaws.challengeappilham.BuildConfig
import com.jaws.challengeappilham.data.network.api.model.category.CategoriesResponse
import com.jaws.challengeappilham.data.network.api.model.menu.MenusResponse
import com.jaws.challengeappilham.data.network.api.model.order.OrderRequest
import com.jaws.challengeappilham.data.network.api.model.order.OrdersResponse
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

interface RestaurantService {

    @GET("listmenu")
    suspend fun getMenus(@Query("c") category: String? = null): MenusResponse

    @GET("category")
    suspend fun getCategories(): CategoriesResponse

    @POST("order")
    suspend fun createOrder(@Body orderRequest: OrderRequest): OrdersResponse

    companion object {
        @JvmStatic
        operator fun invoke(chucker: ChuckerInterceptor): RestaurantService {
            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(chucker)
                .connectTimeout(120, TimeUnit.SECONDS)
                .readTimeout(120, TimeUnit.SECONDS)
                .build()
            val retrofit = Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .addConverterFactory(
                    GsonConverterFactory.create())
                .client(okHttpClient)
                .build()
            return retrofit.create(
                RestaurantService::class.java)
        }
    }
}