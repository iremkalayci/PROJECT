package com.example.myapplication

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.GET

interface AdminProductAPI {

    @GET("iremkalayci/Seller/main/ProductSeller.json")  // URL kısmı
    fun getProducts(): Call<List<ProductInfo>>  // Callback yerine Call kullanılması gerekmektedir
}